package me.jessyan.armscomponent.commonservice.sale.bean;

import java.util.ArrayList;

public class SaleList {
    ArrayList<SaleBean> gameListBeanArrayList=new ArrayList<>();

    public ArrayList<SaleBean> getGameListBeanArrayList() {
        return gameListBeanArrayList;
    }

    public void setGameListBeanArrayList(ArrayList<SaleBean> gameListBeanArrayList) {
        this.gameListBeanArrayList = gameListBeanArrayList;
    }
}
