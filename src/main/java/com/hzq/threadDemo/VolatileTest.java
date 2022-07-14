package com.hzq.threadDemo;

/**
 * description
 *
 * @author hzq
 * @date 2021/11/16 16:48
 */
public class VolatileTest {

    int a;
    volatile int v1 = 1;
    volatile int v2 = 2;

    public static void main(String[] args) {

        VolatileTest volatileTest = new VolatileTest();
        volatileTest.readAndWrite();

    }

    void readAndWrite(){
        int i = v1;
        // 第一个volatile读
        int j = v2;
        // 第二个volatile读
        a = i + j;
        // 普通写
        v1 = i + 1;
        // 第一个volatile写
        v2 = j * 2;

        System.out.println(a);
        System.out.println(v1);
        System.out.println(v2);
    }
}
