package com.popo.module_detail.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.popo.module_detail.R;
import com.popo.module_detail.di.component.DaggerDetailComponent;
import com.popo.module_detail.mvp.contract.DetailContract;
import com.popo.module_detail.mvp.model.entity.GameDetailBean;
import com.popo.module_detail.mvp.presenter.DetailPresenter;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.Utils;

@Route(path = RouterHub.DETAIL_ACTIVITY)
public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailContract.View ,SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout swipeRefreshLayout;
    XBanner xBanner;
    TextView title;
    ImageView headerImg;
    TextView pinDes;
    WebView gameDes;
    WebView sysReq;
    Button review;

    Boolean isBundle;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        if(getIntent().getStringExtra("source").equals("Steam")){
            return R.layout.detail_main_steam;
        }else if(getIntent().getStringExtra("source").equals("Sanko")) {
            return R.layout.detail_main_sanko;
        }else {
            return 0;
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if(getIntent().getStringExtra("source").equals("Steam")){
            isBundle=getIntent().getBooleanExtra("bundle",false);

            xBanner=(XBanner)findViewById(R.id.xbanner);
            title=(TextView)findViewById(R.id.name);
            headerImg=(ImageView)findViewById(R.id.heanderimg);
            pinDes=(TextView)findViewById(R.id.sinpdes);
            gameDes=(WebView)findViewById(R.id.gamedes);
            sysReq=(WebView)findViewById(R.id.sysreq);
            review=(Button)findViewById(R.id.review);
            swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setEnabled(false);

            showLoading();
            mPresenter.requestSteamGameDetail(getIntent().getStringExtra("id"),getIntent().getStringExtra("name").replaceAll("\\/","_"),isBundle);
        }else if(getIntent().getStringExtra("source").equals("Sanko")){
            xBanner=(XBanner)findViewById(R.id.sankoxbanner);
            title=(TextView)findViewById(R.id.sankoname);
            headerImg=(ImageView)findViewById(R.id.sankoheanderimg);
            gameDes=(WebView)findViewById(R.id.sankogamedes);
            sysReq=(WebView)findViewById(R.id.sankosysreq);
            review=(Button)findViewById(R.id.sankoreview);
            swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.sankoswipeRefreshLayout);
            swipeRefreshLayout.setEnabled(false);

            showLoading();
            mPresenter.requestSankoGameDetail(getIntent().getStringExtra("name"),getIntent().getStringExtra("id"));
        }
    }

    @Override
    public void onSteamFinished(GameDetailBean bean) {
        List<String> imgs=new ArrayList<>();
        for(int i=0;i<bean.getBannnerImages().size();++i){
            imgs.add(bean.getBannnerImages().get(i));
            if(i==3){
                break;
            }
        }
        xBanner.setData(imgs,null);
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(DetailActivity.this).load(bean.getBannnerImages().get(position)).into((ImageView)view);
            }
        });
        if(isBundle){
            xBanner.setVisibility(View.GONE);
        }

        title.setText(bean.getTitle());

        ImageLoader imageLoader=ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        if(bean.getImg()!=null){
            imageLoader.loadImage(this,
                    CommonImageConfigImpl
                            .builder()
                            .url(bean.getImg())
                            .imageView(headerImg)
                            .build());
        }

        pinDes.setText(bean.getGameDescription());

        if(isBundle){
            pinDes.setVisibility(View.GONE);
        }

        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width:100% !important; width:auto; height:auto;}</style>" +
                "</head>";
        String gameinfo="<html>" + head + "<body style:'height:auto;max-width: 100%; width:auto;'>" + bean.getGameInfo() + "</body></html>";

        gameDes.loadData(gameinfo,"text/html", "UTF-8");

        if(bean.getGameInfo()==null){
            gameDes.setVisibility(View.GONE);
        }

        String gamedes="<html>" + head + "<body style:'height:auto;max-width: 100%; width:auto;'>" + bean.getSyaRequest() + "</body></html>";
        sysReq.loadData(gamedes,"text/html", "UTF-8");

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,DetialReviewActivity.class);
                intent.putStringArrayListExtra("reviews", bean.getReviews());
                startActivity(intent);
            }
        });
        hideLoading();
    }

    @Override
    public void onSankoFinished(GameDetailBean bean) {
        List<String> images=new ArrayList<>();
        for(int i=0;i<bean.getBannnerImages().size();++i){
            images.add(bean.getBannnerImages().get(i));
            if(i==3){
                break;
            }
        }
        xBanner.setData(images,null);
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(DetailActivity.this).load(bean.getBannnerImages().get(position)).into((ImageView)view);
            }
        });

        title.setText(bean.getTitle());

        ImageLoader imageLoader=ArmsUtils.obtainAppComponentFromContext(this).imageLoader();
        imageLoader.loadImage(this,
                CommonImageConfigImpl
                        .builder()
                        .url(bean.getImg())
                        .imageView(headerImg)
                        .build());

        gameDes.loadData(bean.getGameDescription(),"text/html", "UTF-8");

        sysReq.loadData(bean.getSyaRequest(),"text/html", "UTF-8");

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,DetialReviewActivity.class);
                intent.putStringArrayListExtra("reviews",bean.getReviews());
                startActivity(intent);
            }
        });
        hideLoading();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public Activity getActivity() {
        return this;
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
}
