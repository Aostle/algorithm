package com.hzq.algorithm.slb;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author hzq
 * @date 2021/10/11 10:56
 */
public class RandomGet {

    public static List<String> server_list = Arrays.asList("A", "B", "C");


    public static String getServer(){
        Random random = new Random();
        int i = random.nextInt(server_list.size());
        return server_list.get(i);
    }
}
