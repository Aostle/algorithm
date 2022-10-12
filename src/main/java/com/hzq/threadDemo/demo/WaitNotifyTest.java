package com.hzq.threadDemo.demo;

/**
 * description
 *
 * @author hzq
 * @date 2021/11/2 16:55
 */
public class WaitNotifyTest {
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        myThread mt2 = new myThread(false,object);
        Thread thread1 = new Thread(mt2,"线程B ");
        for (int i = 0;i<10;i++) {
            myThread mt = new myThread(true,object);
            Thread thread = new Thread(mt,"线程A "+i);
            thread.start();
        }
        Thread.sleep(1000);
        thread1.start();
    }
}
