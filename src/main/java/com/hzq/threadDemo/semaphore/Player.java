package com.hzq.threadDemo.semaphore;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * description: 对象: 运动员 , 动作: 跑步
 *
 * @author hzq
 * @date 2021/11/26 10:12
 */
public class Player implements Callable<Result> ,Comparable<Player>{

    private String name;
    private Integer number;
    private Result result;
    private Semaphore runway;
    private final float minSpeed = 8f;
    private Logger logger = Logger.getLogger(Player.class);

    private CountDownLatch startingGun;

    public void setStartingGun(CountDownLatch startingGun) {
        this.startingGun = startingGun;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public Result getResult() {
        return result;
    }

    public Player(String name, Integer number, Result result, Semaphore runway) {
        this.name = name;
        this.number = number;
        this.result = result;
        this.runway = runway;
    }

    @Override
    public int compareTo(Player o) {
        Result myResult = this.result;
        Result targetResult = o.result;

        if(myResult ==null){
            return 1;
        }
        if(targetResult ==null){
            return -1;
        }

        //时间越短,成绩越排名靠前
        if(myResult.getTime()>targetResult.getTime()){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public Result call(){
        try {
            //获取跑道
            runway.acquire();

            if (startingGun!=null){
                logger.info(this.getName()+"准备起跑");
                startingGun.countDown();
                //阻塞,等待发令
                startingGun.await();
                logger.info(this.getName()+"开始跑");
            }

            return this.result = this.doRun();

        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info(name+"[发生异常,中途退赛]");
        } finally {

            logger.info(this.getName()+"比赛正常完成");
            runway.release();
        }
        //出现异常
        this.result.setTime(Float.MAX_VALUE);
        return result;
    }


    private Result doRun() throws InterruptedException {

        //每次状态不同,获取[8-14]随机速度
        float presentSpeed = minSpeed *(1.0f+ new Random().nextFloat());
        if(presentSpeed>14f){
            presentSpeed =14f;
        }

        float time = new BigDecimal(100).divide(BigDecimal.valueOf(presentSpeed),3, RoundingMode.HALF_UP).floatValue();

        synchronized (this){
            this.wait((long) (time*1000));
        }
        result.setTime(time);

        return result;
    }
}
