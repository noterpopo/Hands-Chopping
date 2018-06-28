package com.popo.module_home.mvp.ui.activity;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.popo.module_home.R;
import com.popo.module_home.mvp.model.entity.GameListBean;

import java.util.List;

public class SaleAdapter extends DefaultAdapter<GameListBean> {
    public SaleAdapter(List<GameListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GameListBean> getHolder(View v, int viewType) {
        return new SaleHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.home_listitem;
    }
}
