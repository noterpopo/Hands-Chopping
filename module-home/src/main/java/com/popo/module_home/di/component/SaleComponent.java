package com.popo.module_home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.popo.module_home.di.module.SaleModule;
import com.popo.module_home.mvp.contract.SaleContract;
import com.popo.module_home.mvp.ui.activity.SaleActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = SaleModule.class,dependencies = AppComponent.class)
public interface SaleComponent {
    void inject(SaleActivity activity);
    @Component.Builder
    interface Builder{
        @BindsInstance
        SaleComponent.Builder view(SaleContract.View view);
        SaleComponent.Builder appComponent(AppComponent appComponent);
        SaleComponent build();
    }

}
