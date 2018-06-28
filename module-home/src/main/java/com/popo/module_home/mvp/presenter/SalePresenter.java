package com.popo.module_home.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.popo.module_home.mvp.contract.SaleContract;

import com.popo.module_home.mvp.model.entity.GameListBean;
import com.popo.module_home.mvp.model.entity.GameSaleList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class SalePresenter extends BasePresenter<SaleContract.Model,SaleContract.View>{
    static public GameSaleList gsl=new GameSaleList();
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    List<GameListBean> mDatas;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Inject SalePresenter(SaleContract.Model model,SaleContract.View view){
        super(model,view);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(){

    }

    public void requestSteamSaleGameList(){
        mModel.getSteamSaleGame()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GameSaleList>(mErrorHandler) {
                    @Override
                    public void onNext(GameSaleList gameSaleList) {
                        gsl.getGameListBeanArrayList().addAll(gameSaleList.getGameListBeanArrayList());
                        mDatas.clear();
                        mDatas.addAll(gsl.getGameListBeanArrayList());
                        mAdapter.notifyDataSetChanged();
                        mRootView.imgLoad();
//                        int i=0;
//                        for(GameListBean bean:gameSaleList.getGameListBeanArrayList()){
//                            i++;
//                            Timber.tag("Steam").w(bean.getId());
//                            Timber.tag("Steam").w(bean.getImgUrl());
//                            Timber.tag("Steam").w(bean.getOff()+" "+i);
//                            Timber.tag("Steam").w(bean.getOldPrice()+" "+i);
//                            Timber.tag("Steam").w(bean.getNowPrice()+" "+i);
//                            Timber.tag("Steam").w(bean.getTitle());
//                        }
                    }
                });
    }

    public void requestSankoSaleGameList(){
        mModel.getSankoSaleGame()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GameSaleList>(mErrorHandler) {
                    @Override
                    public void onNext(GameSaleList gameSaleList) {
                        gsl.setGameListBeanArrayList(gameSaleList.getGameListBeanArrayList());
                        requestSteamSaleGameList();
//                        int i=0;
//                        for(GameListBean bean:gameSaleList.getGameListBeanArrayList()){
//                            i++;
//                            Timber.tag("Sanko").w(bean.getId());
//                            Timber.tag("Sanko").w(bean.getImgUrl());
//                            Timber.tag("Sanko").w(bean.getOff()+" "+i);
//                            Timber.tag("Sanko").w(bean.getOldPrice()+" "+i);
//                            Timber.tag("Sanko").w(bean.getNowPrice()+" "+i);
//                            Timber.tag("Sanko").w(bean.getTitle());
//                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mDatas = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
