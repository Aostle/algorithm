package com.hzq.threadDemo.demo;

import java.util.concurrent.CyclicBarrier;

/**
 * description
 *
 * @author hzq
 * @date 2022/8/16 17:41
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            new MyThread().start();
        }
    }
}

class MyThread extends Thread {
    static CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
        @Override
        public void run() {
            System.out.println("栅栏开启");
        }
    });

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(getName() + " 到达栅栏");
            barrier.await();
            System.out.println(getName() + " 冲破栅栏");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
