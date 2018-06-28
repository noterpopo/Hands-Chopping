package com.popo.module_search.mvp.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.popo.module_search.mvp.mvp.contract.SearchContarct;
import com.popo.module_search.mvp.mvp.model.SearchModel;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchBean;
import com.popo.module_search.mvp.mvp.ui.activity.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Module
public abstract class SearchModule {
    @Binds
    abstract SearchContarct.Model bindSaleModel(SearchModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(SearchContarct.View view){
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<GameSearchBean> provideList(){
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static RecyclerView.Adapter provideSaleAdapter(SearchContarct.View searchview,List<GameSearchBean> list){
        SearchAdapter adapter=new SearchAdapter(list);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener<GameSearchBean>() {
            @Override
            public void onItemClick(View view, int viewType, GameSearchBean data, int position) {
                if(data.getSource().equals("Steam")){
                    ARouter.getInstance()
                            .build(RouterHub.DETAIL_ACTIVITY)
                            .withString("source",data.getSource())
                            .withString("id", data.getId())
                            .withString("name",data.getTitle())
                            .withBoolean("bundle",data.getBundle())
                            .navigation(searchview.getActivity());
                }else if(data.getSource().equals("Sanko")){
                    ARouter.getInstance()
                            .build(RouterHub.DETAIL_ACTIVITY)
                            .withString("source",data.getSource())
                            .withString("id", data.getId())
                            .withString("name",data.getSanko_id())
                            .navigation(searchview.getActivity());
                }
            }
        });
        return adapter;
    }

}