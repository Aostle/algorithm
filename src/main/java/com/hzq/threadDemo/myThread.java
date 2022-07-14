package com.hzq.threadDemo;

/**
 * description
 *
 * @author hzq
 * @date 2021/11/2 16:54
 */
class myThread implements Runnable{
    private final boolean flag ;
    private final Object object ;

    myThread(boolean flag, Object o){
        this.flag = flag;
        this.object = o;
    }
    // wait,notify must use in synchronized code
    // join 会使调用线程wait
    private void waitThread(){
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + "wait begin...");
            try {
                object.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "wait end...");
        }
    }
    private void notifyThread(){
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + "notify begin...");
            object.notifyAll();
            System.out.println(Thread.currentThread().getName() + "notify end...");
        }
    }
    @Override
    public void run() {
        if(flag){
            waitThread();
        }else {
            notifyThread();
        }
    }
}

