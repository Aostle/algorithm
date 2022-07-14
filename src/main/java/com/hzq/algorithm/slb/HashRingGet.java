package com.hzq.algorithm.slb;

import java.util.*;

/**
 *
 * @author hzq
 * @date 2021/10/19 10:24
 */
public class HashRingGet {


    public static String getServer(String  client_ip){

        SortedMap<Integer,String> serverMap = new TreeMap<>();
        for (String server_ip : ServerIps.ip_list) {
            serverMap.put(Math.abs(server_ip.hashCode()) ,server_ip );
        }

        SortedMap<Integer, String> tailMap = serverMap.tailMap(Math.abs(client_ip.hashCode()));
        if(tailMap.isEmpty()){
            return serverMap.get(serverMap.firstKey());
        }else{
            return tailMap.get(tailMap.firstKey());
        }
    }



}
