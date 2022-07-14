package com.hzq.threadDemo.semaphore;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * description: 进行一次比赛
 *
 * @author hzq
 * @date 2021/11/26 10:14
 */
public class OneTrack {

    public static void main(String[] args) {
        new OneTrack().track();
    }

    private final Logger logger = Logger.getLogger(OneTrack.class);

    //参赛运动员
    private static final String[] PLAYERNAMES = new String[]{"白银圣斗士","黄金圣斗士"
            ,"青铜圣斗士","神斗士","冥斗士","哈迪斯","龟仙人","孙悟空","孙悟饭","贝吉塔","孙悟天"};


    //初赛结果
    private final PriorityBlockingQueue<Player> preliminary = new PriorityBlockingQueue<>();


    //决赛结果
    private final PriorityBlockingQueue<Player> finals = new PriorityBlockingQueue<>();



    public void track(){
        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Semaphore runway = new Semaphore(5,false);

        logger.info("初赛开始");
        for (int i = 0; i < PLAYERNAMES.length; i++) {
            Future<Result> future;
            Player player = new Player(PLAYERNAMES[i],i+1,new Result(), runway);
            future = executorService.submit(player);
            new FutureThread(player,future,preliminary).start();
        }

        synchronized (preliminary){
            while (preliminary.size()<PLAYERNAMES.length){
                try {
                    preliminary.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("初赛结束,决赛开始");
        for (int i = 0; i < 5; i++) {
            Future<Result> future;
            Player player = preliminary.poll();
            assert player != null;
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
}
