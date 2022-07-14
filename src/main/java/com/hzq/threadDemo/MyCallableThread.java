package com.hzq.threadDemo;

import java.util.concurrent.*;

/**
 *
 * @author hzq
 * @date 2021/11/25 11:02
 */
public class MyCallableThread<V extends Entity> implements Callable<V> {

    private V result;


    public MyCallableThread(V param) {
        this.result = param;
    }

    @Override
    public V call() throws Exception {

        try {
            synchronized (this){
                this.wait(5000);
            }
            this.result.setStatus(1);
        } catch (InterruptedException e) {
            this.result.setStatus(-1);
        }
        return this.result;
    }


    public static void main(String[] args) {
        MyCallableThread<Entity> callableThread = new MyCallableThread<>(new Entity());

        /*// Callable需要在线程池中执行
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Entity> future = es.submit(callableThread);

        // main线程会在这里等待，知道callableThread任务执行完成
        Entity result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("result.status = " + result.getStatus());
        // 停止线程池工作
        es.shutdown();
        try {
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        FutureTask<Entity> futureTask = new FutureTask<Entity>(callableThread);
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get().getStatus());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }





}
