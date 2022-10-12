package com.hzq.threadDemo.ymp;

import java.util.*;
import java.util.concurrent.*;

/**
 * description: 模拟文件解析
 *
 * @author hzq
 * @date 2022/7/14 23:26
 */
public class FileParser {

    private static FileParser singleton = null;


    public static FileParser getSingleton(){
        if(singleton==null){
            singleton = new FileParser();
        }
        return singleton;
    }

    public Boolean parse(String  file) throws InterruptedException {
        Random random = new Random();
        String name = Thread.currentThread().getName();
        //随机执行时间
        System.out.println(name+":开始解析文件["+file+"]");
        int time = 5+ random.nextInt(10);
        Thread.sleep(time*1000);
        //随机获取结果
        boolean isSuccess = Math.random()>0.4;
        System.out.println(name+":解析文件["+file+"]完成"+",结果:"+isSuccess+",耗时:"+time+"秒");
        return isSuccess;
    }


    public Boolean getResult() throws InterruptedException {
        String[] fileNames = {"基础监控文档.doc","监控工单优化.xls"};
        int size = fileNames.length;
        Vector<Boolean> list = new Vector<>(size);
        CountDownLatch counter = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {

            final int finalI = i;
            new Thread(() -> {
                Boolean result = null;
                try {
                    result = parse(fileNames[finalI]);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                list.add(result);
                counter.countDown();
            }).start();
        }

        counter.await();

        for (Boolean result : list) {
            if(!result){
                return false;
            }
        }
        return true;
    }


    public Boolean getResult2() throws Exception {
        String[] fileNames = {"基础监控文档.doc","监控工单优化.xls"};
        int size = fileNames.length;
        List<Future<Boolean>> futureTasks = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(size);

        for (String fileName : fileNames) {
            Future<Boolean> task = pool.submit(() -> parse(fileName));
            futureTasks.add(task);
        }

        pool.shutdown();
        Set<Future<Boolean>> futureSet = new LinkedHashSet<>();
        do {
            for (Future<Boolean> futureTask : futureTasks) {
                if (futureTask.isDone()) {
                    futureSet.add(futureTask);
                    Boolean result = futureTask.get();
                    if (!result) {
                        pool.shutdownNow();
                        return false;
                    }
                }
            }
        } while (futureSet.size() != futureTasks.size());
        return true;
    }

    private void closeTask(List<Future<Boolean>> futureTasks){
        for (Future<Boolean> task : futureTasks) {
            task.cancel(true);
        }

    }
}
