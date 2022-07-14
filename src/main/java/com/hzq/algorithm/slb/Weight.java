package com.hzq.algorithm.slb;

/**
 * description
 *
 * @author hzq
 * @date 2021/10/11 17:23
 */
public class Weight {

    private String ip;

    private Integer weight;

    private Integer current_weight;

    public Weight(String ip, Integer weight, Integer current_weight) {
        this.ip = ip;
        this.weight = weight;
        this.current_weight = current_weight;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCurrent_weight() {
        return current_weight;
    }

    public void setCurrent_weight(Integer current_weight) {
        this.current_weight = current_weight;
    }
}
