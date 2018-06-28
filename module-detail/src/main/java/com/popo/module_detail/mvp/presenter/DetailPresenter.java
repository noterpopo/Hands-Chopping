package com.popo.module_detail.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.popo.module_detail.app.utils.SankoListener;
import com.popo.module_detail.mvp.contract.DetailContract;
import com.popo.module_detail.mvp.model.entity.GameDetailBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;

public class DetailPresenter extends BasePresenter<DetailContract.Model,DetailContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject DetailPresenter(DetailContract.Model model,DetailContract.View view){
        super(model,view);
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(){

    }
    public void requestSteamGameDetail(String id,String name,boolean isBundle){
        mModel.getSteamGameDetail(id,name,isBundle)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()->{
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GameDetailBean>(mErrorHandler) {
                    @Override
                    public void onNext(GameDetailBean gameDetailBean) {
                        if(!isBundle){
                            requestSteamGameReview(id, name,gameDetailBean);
                        }else {
                            mRootView.onSteamFinished(gameDetailBean);
                            mRootView.hideLoading();
                        }
                    }
                });
    }
    public void requestSteamGameReview(String id,String name,GameDetailBean glb){
        mModel.getSteamGameReview(id, name)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()->{
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GameDetailBean>(mErrorHandler) {
                    @Override
                    public void onNext(GameDetailBean gameDetailBean) {
                        glb.setReviews(gameDetailBean.getReviews());
                        glb.setId(id);
                        glb.setTitle(name);
                        mRootView.onSteamFinished(glb);
                    }
                });
    }

    public void requestSankoGameDetail(String product_id,String id) {
       mModel.getSankoGame(product_id, id, new SankoListener() {
           @Override
           public void onfinished(GameDetailBean bean) {
               mRootView.onSankoFinished(bean);
           }
       });
    }
}
