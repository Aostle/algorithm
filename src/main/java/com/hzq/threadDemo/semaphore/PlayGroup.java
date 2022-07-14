package com.hzq.threadDemo.semaphore;

import java.util.List;

/**
 * description
 *
 * @author hzq
 * @date 2021/11/29 11:51
 */
public class PlayGroup {


    private Integer number;

    private List<Player> players;


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
