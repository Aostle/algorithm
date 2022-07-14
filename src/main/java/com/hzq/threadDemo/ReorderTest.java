package com.hzq.threadDemo;

/**
 * description
 *
 * @author hzq
 * @date 2021/11/16 9:57
 */
public class ReorderTest {

    int a= 0;
    boolean flag = false;

    public void writer(){
        a = 1;
        flag =true;
    }

    public  void reader () {
        if (flag){
            int i = a*a;
            System.out.println(i);
        }
    }

    public void doSomething(){
        try {
            System.out.println(Thread.currentThread().getName()+"---doSomething");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReorderTest reorderTest = new ReorderTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"---write");
                reorderTest.writer();

            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"---read");
                reorderTest.reader();
            }
        });

        t1.start();
        t2.start();
    }
}
