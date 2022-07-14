package com.hzq.algorithm.slb;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * description
 *
 * @author hzq
 * @date 2021/10/19 11:02
 */
public class HashVirtualGet {

    public static String getServer(String  client_ip){

        SortedMap<Integer,String> serverMap = new TreeMap<>();

        for (String server_ip : ServerIps.ip_list) {
            serverMap.put(Math.abs(server_ip.hashCode()) ,server_ip );
            for (int i = 0; i < ServerIps.virtual_count; i++) {
                String virtual_server = server_ip+"#"+i;
                Integer virtual_hash = Math.abs(virtual_server.hashCode());
                serverMap.put(virtual_hash,"虚拟节点"+i+"映射过来的请求"+server_ip);
            }
        }

        SortedMap<Integer, String> tailMap = serverMap.tailMap(Math.abs(client_ip.hashCode()));
        if(tailMap.isEmpty()){
            return serverMap.get(serverMap.firstKey());
        }else{
            return tailMap.get(tailMap.firstKey());
        }
    }
}
