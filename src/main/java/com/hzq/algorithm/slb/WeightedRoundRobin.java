package com.hzq.algorithm.slb;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hzq
 * @date 2021/10/11 11:35
 */
public class WeightedRoundRobin {



    public static String getServer(int times){
        int sum = ServerIps.weight_list.values().stream().mapToInt(str->str).sum();
        times = times%sum;
        for (String server : ServerIps.weight_list.keySet()) {
            times = times - ServerIps.weight_list.get(server);
            if(times<0){
                return server;
            }
        }
        return null;
    }
}
