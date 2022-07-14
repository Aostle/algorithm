package com.hzq.threadDemo;

import org.apache.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description: 线程池测试
 *
 * @author hzq
 * @date 2021/11/25 10:13
 */
public class ExtendsPool extends ThreadPoolExecutor {

    private static Logger logger = Logger.getLogger(ExtendsPool.class);

    public ExtendsPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        TestRunnable testRunnable = (TestRunnable)r;
        logger.info("beforeExecute(Thread t, Runnable r) : " + testRunnable.getIndex());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        TestRunnable testRunnable = (TestRunnable)r;
        logger.info("afterExecute(Thread t, Runnable r) : " + testRunnable.getIndex());
    }

    @Override
    protected void terminated() {
        logger.info("线程池已终止!");
    }


    public static void main(String[] args) {
        ExtendsPool extendsPool = new ExtendsPool(5, 5, 10, TimeUnit.SECONDS
                , new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 10; i++) {
            extendsPool.execute(new TestRunnable(i));
        }

        extendsPool.shutdown();

        try {
            extendsPool.awaitTermination(Long.MAX_VALUE,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class TestRunnable implements Runnable{

        public Integer getIndex() {
            return index;
        }

        private Integer index;

        public TestRunnable(Integer index) {
            this.index = index;
        }

        @Override
        public void run() {
            Thread t = Thread.currentThread();
            synchronized (t){
                try {
                    t.wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
