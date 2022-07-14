package com.hzq.algorithm.slb;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hzq
 * @date 2021/10/11 16:32
 */
public class WeightSmooth {

    public static Map<String,Weight> current_weights = new HashMap<>();


    public static String getServer(){

        int totalWeights = ServerIps.weight_list.values().stream().mapToInt(w->w).sum();

        //初始化动态权重 000
        if(current_weights.isEmpty()){
            for (String ip : ServerIps.weight_list.keySet()) {
                current_weights.put(ip,new Weight(ip,ServerIps.weight_list.get(ip),0));
            }
        }

        //叠加原始权重
        for (Weight weight : current_weights.values()) {
            weight.setCurrent_weight(weight.getWeight()+weight.getCurrent_weight());
        }

        //获取最大权重ip
        Weight maxWeight = null;
        for (Weight weight : current_weights.values()) {
            if(maxWeight==null||weight.getCurrent_weight()> maxWeight.getCurrent_weight()){
                maxWeight = weight;
            }
        }

        assert maxWeight != null;


        //最大权重减去权重之和
        maxWeight.setCurrent_weight(maxWeight.getCurrent_weight()-totalWeights);


        return maxWeight.getIp();
    }

}
