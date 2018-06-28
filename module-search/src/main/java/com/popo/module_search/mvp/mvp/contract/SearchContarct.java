package com.popo.module_search.mvp.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchList;

import io.reactivex.Observable;

public interface SearchContarct {
    interface View extends IView {
        void startLoadMore();
        void endLoadMore();
        Activity getActivity();
    }
    interface Model extends IModel {
        Observable<GameSearchList> getSteamSearchGame(String key);
        Observable<GameSearchList> getSankoSearchGame(String key);

    }
}
