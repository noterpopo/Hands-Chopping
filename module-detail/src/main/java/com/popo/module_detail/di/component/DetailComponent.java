package com.popo.module_detail.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.popo.module_detail.di.module.DetailModule;
import com.popo.module_detail.mvp.contract.DetailContract;
import com.popo.module_detail.mvp.ui.DetailActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = DetailModule.class,dependencies = AppComponent.class)
public interface DetailComponent {
    void inject(DetailActivity activity);
    @Component.Builder
    interface Builder{
        @BindsInstance
        DetailComponent.Builder view(DetailContract.View view);
        DetailComponent.Builder appComponent(AppComponent appComponent);
        DetailComponent build();
    }
}
