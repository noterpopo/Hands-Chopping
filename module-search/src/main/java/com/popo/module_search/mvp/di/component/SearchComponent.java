package com.popo.module_search.mvp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.popo.module_search.mvp.di.module.SearchModule;
import com.popo.module_search.mvp.mvp.contract.SearchContarct;
import com.popo.module_search.mvp.mvp.ui.activity.SearchActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = SearchModule.class,dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(SearchActivity activity);
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder view(SearchContarct.View view);
        Builder appComponent(AppComponent appComponent);
        SearchComponent build();
    }

}
