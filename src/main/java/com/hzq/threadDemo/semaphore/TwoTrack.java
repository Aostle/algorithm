package com.hzq.threadDemo.semaphore;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 *
 * @author hzq
 * @date 2021/11/26 17:06
 */
public class TwoTrack {

    private final Logger logger = Logger.getLogger(TwoTrack.class);

    //参赛运动员
    private static final String[] PLAYERNAMES = new String[]{"白银圣斗士","黄金圣斗士"
            ,"青铜圣斗士","神斗士","冥斗士","哈迪斯","龟仙人","孙悟空","孙悟饭","贝吉塔","孙悟天","猪八戒","沙和尚"};

    private final ArrayList<Player> signupPlayers = new ArrayList<>();


    //初赛结果
    private final PriorityBlockingQueue<Player> preliminary = new PriorityBlockingQueue<>();


    //决赛结果
    private final PriorityBlockingQueue<Player> finals = new PriorityBlockingQueue<>();



    public void track(){
        long start = System.currentTimeMillis();

        Semaphore runway = new Semaphore(5,false);
        signupPlayers.clear();
        for (int i = 0; i < PLAYERNAMES.length; i++){
            Player player = new Player(PLAYERNAMES[i],i+1,new Result(), runway);
            signupPlayers.add(player);
        }


        int runwayCount =5;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch startingGun = null;

        logger.info("初赛开始");
        for (int i = 0; i < signupPlayers.size(); i++) {

            if(i%runwayCount==0){
                startingGun = new CountDownLatch(Math.min(this.signupPlayers.size() - i, runwayCount));
            }

            Future<Result> future;
            Player player = signupPlayers.get(i);
            player.setStartingGun(startingGun);
            future = executorService.submit(player);

            //监控并统计成绩
            new FutureThread(player,future,preliminary).start();
        }

        synchronized (preliminary){
            while (preliminary.size()<signupPlayers.size()){
                try {
                    preliminary.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("初赛结束,决赛开始");
        startingGun = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
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
        new TwoTrack().track();
    }
}
