package com.hzq.threadDemo.semaphore;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * description: 监控运动员,统计结果
 *
 * @author hzq
 * @date 2021/11/26 10:14
 */
public class FutureThread extends Thread{

    private Player player;

    private Future<Result> future;

    private PriorityBlockingQueue<Player>  achievementQueue;

    private Logger logger = Logger.getLogger(FutureThread.class);


    public FutureThread(Player player, Future<Result> future, PriorityBlockingQueue<Player> achievementQueue) {
        this.player = player;
        this.future = future;
        this.achievementQueue = achievementQueue;
    }


    @Override
    public void run() {
        if(future==null){
            logger.info("[选手中途退赛,计分为0]");
        }else{
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        this.achievementQueue.put(this.player);
        synchronized (this.achievementQueue){
            this.achievementQueue.notify();
        }

    }
}
