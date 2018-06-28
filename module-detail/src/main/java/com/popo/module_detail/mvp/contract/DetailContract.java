package com.popo.module_detail.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.popo.module_detail.app.utils.SankoListener;
import com.popo.module_detail.mvp.model.entity.GameDetailBean;

import io.reactivex.Observable;

public interface DetailContract {
    interface View extends IView {
        Activity getActivity();
        void onSteamFinished(GameDetailBean bean);
        void onSankoFinished(GameDetailBean bean);
    }
    interface Model extends IModel {
        Observable<GameDetailBean> getSteamGameDetail(String id,String name,Boolean isBundle);
        Observable<GameDetailBean> getSteamGameReview(String id,String name);
        Observable<GameDetailBean> getSankoGameDetail(String id,String name);
        void getSankoGame(String id,String name,SankoListener listener);

    }
}
