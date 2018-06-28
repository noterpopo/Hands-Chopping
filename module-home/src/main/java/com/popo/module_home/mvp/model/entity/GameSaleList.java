package com.popo.module_home.mvp.model.entity;

import java.util.ArrayList;

public class GameSaleList {
    ArrayList<GameListBean> gameListBeanArrayList=new ArrayList<>();

    public ArrayList<GameListBean> getGameListBeanArrayList() {
        return gameListBeanArrayList;
    }

    public void setGameListBeanArrayList(ArrayList<GameListBean> gameListBeanArrayList) {
        this.gameListBeanArrayList = gameListBeanArrayList;
    }
}
