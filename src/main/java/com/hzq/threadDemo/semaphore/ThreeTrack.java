package com.hzq.threadDemo.semaphore;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * description: 分组,上一组跑完才能进行下一组
 *
 * @author hzq
 * @date 2021/11/29 11:40
 */
public class ThreeTrack {

    private final Logger logger = Logger.getLogger(TwoTrack.class);

    //参赛运动员
    private static final String[] PLAYERNAMES = new String[]{"白银圣斗士","黄金圣斗士"
            ,"青铜圣斗士","神斗士","冥斗士","哈迪斯","龟仙人","孙悟空","孙悟饭","贝吉塔","孙悟天","猪八戒","沙和尚"};


    //初赛结果
    private final PriorityBlockingQueue<Player> preliminary = new PriorityBlockingQueue<>();


    //决赛结果
    private final PriorityBlockingQueue<Player> finals = new PriorityBlockingQueue<>();



    public void track(){
        long start = System.currentTimeMillis();

        int runwayCount =5;
        Semaphore runway = new Semaphore(runwayCount,false);


        logger.info("初赛开始");
        List<PlayGroup> groupList = new ArrayList<>();
        PlayGroup tempGroup;
        List<Player> tempPlayers  = null;

        for (int i = 0; i < PLAYERNAMES.length; i++){

            if(i%runwayCount==0){
                tempGroup = new PlayGroup();
                tempGroup.setNumber((i/runwayCount)+1);
                tempPlayers = new ArrayList<>();
                tempGroup.setPlayers(tempPlayers);
                groupList.add(tempGroup);
            }

            Player player = new Player(PLAYERNAMES[i],i+1,new Result(), runway);
            tempPlayers.add(player);
        }

        logger.info("分组完成");

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (PlayGroup group : groupList) {
            List<Player> players = group.getPlayers();
            logger.info("第" + group.getNumber() + "组开始比赛");
            CountDownLatch startingGun = new CountDownLatch(players.size());
            for (Player player : players) {
                player.setStartingGun(startingGun);
                Future<Result> future = executorService.submit(player);
                new FutureThread(player, future, preliminary).start();
            }


            int limit = (group.getNumber() - 1) * 5 + players.size();
            synchronized (preliminary) {
                while (preliminary.size() < limit) {
                    try {
                        preliminary.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            logger.info("第" + group.getNumber() + "组完成比赛");
        }


        logger.info("初赛结束,决赛开始");
        CountDownLatch startingGun = new CountDownLatch(runwayCount);
        for (int i = 0; i < runwayCount; i++) {
            Future<Result> future;
            Player player = preliminary.poll();
            assert player != null;
            player.setStartingGun(startingGun);
            future = executorService.submit(player);
            new FutureThread(player,future,finals).start();
        }


        synchronized (finals){
            while(finals.size()<5){
                try {
                    finals.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        long end = System.currentTimeMillis();
        logger.info("决赛结束,用时:"+(end-start)/1000+"秒");
        for (int i = 1; i <= 3; i++) {
            Player player = finals.poll();
            assert player != null;
            logger.info("第"+i+"名:"+player.getName()+"成绩为:"+player.getResult().getTime());
        }

        executorService.shutdown();
    }

    public static void main(String[] args) {
        new ThreeTrack().track();
    }
}
