package com.hzq.algorithm.slb;

import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author hzq
 * @date 2021/10/19 9:59
 */
public class HashGet {



    public static String getServer(String ip){
        int index = Math.abs(ip.hashCode())%ServerIps.server_list.size();
        return ServerIps.server_list.get(index);
    }



//    String对象的哈希码计算如下
//    s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
//    使用int算术，其中s[i]是字符串的第i个字符， n是字符串的长度， ^表示求幂。 （空字符串的哈希值为零。）
    public static void main(String[] args) {
        System.out.println("ab".hashCode());
    }
}
