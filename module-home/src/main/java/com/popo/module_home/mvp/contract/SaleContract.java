package com.popo.module_home.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.popo.module_home.mvp.model.entity.GameSaleList;

import io.reactivex.Observable;

public interface SaleContract {
    interface View extends IView{
        void startLoadMore();
        void endLoadMore();
        Activity getActivity();
        void imgLoad();
    }
    interface Model extends IModel{
        Observable<GameSaleList> getSteamSaleGame();
        Observable<GameSaleList> getSankoSaleGame();

    }
}
