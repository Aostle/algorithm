package com.hzq.threadDemo.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * description
 *
 * @author hzq
 * @date 2022/8/16 17:32
 */
public class SemaphoreExample {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(300);
        // 设置 20 个信号量
        final Semaphore semaphore = new Semaphore(20);
        // final Semaphore semaphore = new Semaphore(20, true);  公平锁

        for (int i = 0; i < 500; i++) {
            final int threadnum = i;
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();             // 获取一个信号量
                    test(threadnum);
                    semaphore.release();             // 释放一个信号量
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        threadPool.shutdown();
        System.out.println("finish");
    }

    public static void test(int threadnum) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("threadnum:" + threadnum);
        Thread.sleep(1000);
    }
}

