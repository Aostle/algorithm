package com.hzq.copy;

/**
 * description
 *
 * @author hzq
 * @date 2022/4/25 11:07
 */
public class TestClient {

    public static void main(String[] args) {
        //产生对象
        //ShallowCopy copy = new ShallowCopy();
        DeepCopy copy = new DeepCopy();
        copy.setValue("张三");

        //拷贝对象
       //ShallowCopy cloneCopy = copy.clone();
       DeepCopy cloneCopy = copy.clone();
        cloneCopy.setValue("李四");

        System.out.println(copy.getvalue());
    }
}
