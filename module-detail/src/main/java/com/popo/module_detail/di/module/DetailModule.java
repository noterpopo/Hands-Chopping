package com.popo.module_detail.di.module;

import com.popo.module_detail.mvp.contract.DetailContract;
import com.popo.module_detail.mvp.model.DetailModel;

import butterknife.BindView;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class DetailModule {
    @Binds
    abstract DetailContract.Model bindDetailModel(DetailModel model);
}
