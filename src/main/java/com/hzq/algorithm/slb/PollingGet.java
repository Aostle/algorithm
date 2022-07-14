package com.hzq.algorithm.slb;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hzq
 * @date 2021/10/11 11:22
 */
public class PollingGet {

    public static List<String> server_list = Arrays.asList("A", "B", "C");

    public static String getServer(Integer times){
        int index = times % server_list.size();
        return server_list.get(index);
    }
}
