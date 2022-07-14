package com.hzq.threadDemo.ymp;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

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

    public Boolean parse(String  file) {
        Random random = new Random();
        String name = Thread.currentThread().getName();
        //随机执行时间
        System.out.println(name+":开始解析文件["+file+"]");
        int time = 5+ random.nextInt(10);
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //随机获取结果
        boolean isSuccess = Math.random()>0.2;
        System.out.println(name+":解析文件["+file+"]完成"+",结果:"+isSuccess+",耗时:"+time+"秒");
        return isSuccess;
    }


    public Boolean getResult() throws InterruptedException {
        String[] fileNames = {"基础监控文档.doc","监控工单优化.xls"};
        int size = fileNames.length;
        Vector<Boolean> list = new Vector<>(size);
        CountDownLatch counter = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            int finalI = i;
            new Thread(() -> {
                Boolean result = parse(fileNames[finalI]);
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
}
