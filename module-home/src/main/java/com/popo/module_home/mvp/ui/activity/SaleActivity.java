package com.popo.module_home.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.popo.module_home.R;
import com.popo.module_home.R2;
import com.popo.module_home.di.component.DaggerSaleComponent;
import com.popo.module_home.mvp.contract.SaleContract;
import com.popo.module_home.mvp.model.entity.GameListBean;
import com.popo.module_home.mvp.presenter.SalePresenter;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.SALEACTIVITY)
public class SaleActivity extends BaseActivity<SalePresenter> implements SaleContract.View {
    @Inject
    RecyclerView.Adapter mAdapter;
    List<String> imgsUrl;
    XBanner xBanner;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSaleComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.home_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        xBanner=(XBanner)findViewById(R.id.xbanner);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestSankoSaleGameList();
            }
        });

        imgsUrl=new ArrayList<>();
        showLoading();
        mPresenter.requestSankoSaleGameList();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void startLoadMore() {
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void imgLoad() {
        for(int i=0;i<3;++i){
            imgsUrl.add(SalePresenter.gsl.getGameListBeanArrayList().get(i).getImgUrl());
        }
        xBanner.setData(imgsUrl,null);
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(SaleActivity.this).load(imgsUrl.get(position)).into((ImageView)view);
            }
        });
        xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, int position) {
                if(SalePresenter.gsl.getGameListBeanArrayList().get(position).getSource().equals("Steam")){
                    ARouter.getInstance()
                            .build(RouterHub.DETAIL_ACTIVITY)
                            .withString("source",SalePresenter.gsl.getGameListBeanArrayList().get(position).getSource())
                            .withString("id", SalePresenter.gsl.getGameListBeanArrayList().get(position).getId())
                            .withString("name",SalePresenter.gsl.getGameListBeanArrayList().get(position).getTitle())
                            .navigation(getActivity());
                }else if(SalePresenter.gsl.getGameListBeanArrayList().get(position).getSource().equals("Sanko")){
                    ARouter.getInstance()
                            .build(RouterHub.DETAIL_ACTIVITY)
                            .withString("source",SalePresenter.gsl.getGameListBeanArrayList().get(position).getSource())
                            .withString("id", SalePresenter.gsl.getGameListBeanArrayList().get(position).getId())
                            .withString("name",SalePresenter.gsl.getGameListBeanArrayList().get(position).getSankoId())
                            .navigation(getActivity());
                }
            }
        });
        hideLoading();
    }


    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(recyclerView);
        super.onDestroy();
    }
}
