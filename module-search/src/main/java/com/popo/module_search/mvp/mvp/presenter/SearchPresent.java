package com.popo.module_search.mvp.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.popo.module_search.mvp.mvp.contract.SearchContarct;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchBean;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;

import static com.popo.module_search.mvp.mvp.model.Api.STEAM_DOMAIN;
import static com.popo.module_search.mvp.mvp.model.Api.STEAM_DOMAIN_NAME;


@ActivityScope
public class SearchPresent extends BasePresenter<SearchContarct.Model,SearchContarct.View> {
    static public GameSearchList gsl=new GameSearchList();
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    List<GameSearchBean> mDatas;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Inject SearchPresent(SearchContarct.Model model,SearchContarct.View view){
        super(model,view);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(){
    }

    public void searchFromSteam(String keyword){

        mModel.getSteamSearchGame(keyword)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(disposable -> {mRootView.showLoading();})
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GameSearchList>(mErrorHandler){
                    @Override
                    public void onNext(GameSearchList gameSearchList) {
                        gsl.setGameSearchBeanArrayList(gameSearchList.getGameSearchBeanArrayList());
                        searchFromSanko(keyword);
                    }
//                        int i=0;
//                        for(GameSearchBean bean:gameSearchList.getGameSearchBeanArrayList()){
//                            i++;
//                            Timber.tag("Steam").w(bean.getId());
//                            Timber.tag("Steam").w(bean.getImgUrl());
//                            Timber.tag("Steam").w(bean.getPrice()+" "+i);
//                            Timber.tag("Steam").w(bean.getTitle());
//                        }
//                    }
                });
    }
    public void searchFromSanko(String keyword){
        mModel.getSankoSearchGame(keyword)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()->{
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GameSearchList>(mErrorHandler){
                    @Override
                    public void onNext(GameSearchList gameSearchList) {
                        gsl.getGameSearchBeanArrayList().addAll(gameSearchList.getGameSearchBeanArrayList());
                        mDatas.clear();
                        mDatas.addAll(gsl.getGameSearchBeanArrayList());
                        mAdapter.notifyDataSetChanged();
//                        int i=0;
//                        for(GameSearchBean bean:gameSearchList.getGameSearchBeanArrayList()){
//                            i++;
//                            Timber.tag("Sanko").w(bean.getId());
//                            Timber.tag("Sanko").w(bean.getImgUrl());
//                            Timber.tag("Sanko").w(bean.getPrice()+" "+i);
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
