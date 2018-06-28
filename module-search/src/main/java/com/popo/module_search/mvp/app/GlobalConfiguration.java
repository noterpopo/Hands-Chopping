package com.popo.module_search.mvp.app;

import android.app.Application;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;

import com.popo.module_search.BuildConfig;
import com.popo.module_search.mvp.app.utils.GameSearchListResponseConverter;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchList;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.BaseConverterFactory;
import me.jessyan.armscomponent.commonsdk.utils.BaseResponseConverter;
import retrofit2.Retrofit;

public class GlobalConfiguration implements ConfigModule{
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppLifecycles 的所有方法都会在基类 Application 的对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        // 可以根据不同的逻辑添加多个实现类
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {


    }


    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //当所有模块集成到宿主 App 时, 在 App 中已经执行了以下代码, 所以不需要再执行
        if (BuildConfig.IS_BUILD_MODULE) {
            lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentDestroyed(FragmentManager fm, android.support.v4.app.Fragment f) {
                    ((RefWatcher) ArmsUtils
                            .obtainAppComponentFromContext(f.getActivity())
                            .extras()
                            .get(RefWatcher.class.getName()))
                            .watch(f);
                }
            });
        }
    }
}
