package com.popo.module_home.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.popo.module_home.mvp.contract.SaleContract;
import com.popo.module_home.mvp.model.SaleModel;
import com.popo.module_home.mvp.model.entity.GameListBean;
import com.popo.module_home.mvp.ui.activity.SaleAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Module
public abstract class SaleModule {
    @Binds
    abstract SaleContract.Model bindSaleModel(SaleModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(SaleContract.View view){
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<GameListBean> provideList(){
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static RecyclerView.Adapter provideSaleAdapter(SaleContract.View saleview,List<GameListBean> list){
        SaleAdapter adapter=new SaleAdapter(list);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<GameListBean>() {
            @Override
            public void onItemClick(View view, int viewType, GameListBean data, int position) {
                if(data.getSource().equals("Steam")){
                    ARouter.getInstance()
                            .build(RouterHub.DETAIL_ACTIVITY)
                            .withString("source",data.getSource())
                            .withString("id", data.getId())
                            .withString("name",data.getTitle())
                            .withBoolean("bundle",data.isBundle())
                            .navigation(saleview.getActivity());
                }else if(data.getSource().equals("Sanko")){
                    ARouter.getInstance()
                            .build(RouterHub.DETAIL_ACTIVITY)
                            .withString("source",data.getSource())
                            .withString("id", data.getId())
                            .withString("name",data.getSankoId())
                            .navigation(saleview.getActivity());
                }
            }
        });
        return adapter;
    }

}
