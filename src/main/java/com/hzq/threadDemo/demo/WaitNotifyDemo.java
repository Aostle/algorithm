package com.hzq.threadDemo.demo;

/**
 * description: test
 *
 * @author hzq
 * @date 2022/8/16 11:47
 */
public class WaitNotifyDemo {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        synchronized(t) {                         // 对 t 设置对象锁
            try {
                t.start();
                System.out.println("1");
                t.wait();                         // 当前线程释放 t 锁，进入 t 对象等待池
                System.out.println("4");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            synchronized (this) {                 // 对 t 设置对象锁
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("2");
                this.notify();                    // 随机唤醒一个 t 对象等待池中的线程
                System.out.println("3");
            }
        }
    }
}

