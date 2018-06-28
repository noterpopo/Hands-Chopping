package com.popo.module_search.mvp.mvp.ui.activity;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.popo.module_search.R;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchBean;

import java.util.List;

public class SearchAdapter extends DefaultAdapter<GameSearchBean> {
    public SearchAdapter(List<GameSearchBean> infos){
        super(infos);
    }
    @Override
    public BaseHolder<GameSearchBean> getHolder(View v, int viewType) {
        return new SearchHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.search_listitem;
    }
}
