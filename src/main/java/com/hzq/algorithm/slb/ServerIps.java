package com.hzq.algorithm.slb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author hzq
 * @date 2021/10/11 17:20
 */
public class ServerIps {

    public static final Map<String,Integer> weight_list = new HashMap<>(){
        {
            put("A", 2);
            put("B", 3);
            put("c", 5);
        }};

    public static final List<String> server_list = Arrays.asList("A","B","C","D","E");

    public static final List<String> ip_list = Arrays.asList("123.111.0.0","123.101.3.1","111.20.35.2","123.98.26.3");

    public static final Integer virtual_count = 3;
}
