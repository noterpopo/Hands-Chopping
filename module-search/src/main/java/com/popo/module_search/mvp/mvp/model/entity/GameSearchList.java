package com.popo.module_search.mvp.mvp.model.entity;

import java.util.ArrayList;

public class GameSearchList {
    ArrayList<GameSearchBean> gameSearchBeanArrayList=new ArrayList<>();

    public ArrayList<GameSearchBean> getGameSearchBeanArrayList() {
        return gameSearchBeanArrayList;
    }

    public void setGameSearchBeanArrayList(ArrayList<GameSearchBean> gameSearchBeanArrayList) {
        this.gameSearchBeanArrayList = gameSearchBeanArrayList;
    }
}
