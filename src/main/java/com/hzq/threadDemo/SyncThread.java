package com.hzq.threadDemo;

import org.apache.log4j.Logger;

/**
 * 用来在启动后，等待唤醒
 * @author yinwenjie
 */
public class SyncThread implements Runnable {



    private Integer value;

    private static Integer NOWVALUE;

    Logger logger = Logger.getLogger(SyncThread.class);

    public SyncThread(int value) {
        this.value = value;
    }

    /**
     * 对这个类的实例化对象进行检查
     */
    private  void doOtherthing() {
        synchronized (SyncThread.class){
            NOWVALUE = this.value;
            logger.info("当前NOWVALUE的值：" + NOWVALUE);
        }


    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        Long id = currentThread.getId();
        this.doOtherthing();
    }

    public static void main(String[] args) throws Exception {
        Thread syncThread1 = new Thread(new SyncThread(10));
        Thread syncThread2 = new Thread(new SyncThread(100));

        syncThread1.start();
        syncThread2.start();
    }
}