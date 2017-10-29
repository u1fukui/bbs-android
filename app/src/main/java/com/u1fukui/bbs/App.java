package com.u1fukui.bbs;


import com.u1fukui.bbs.di.DaggerAppComponent;
import com.u1fukui.bbs.utils.ToastUtils;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {

    private static App instance;

    private ToastUtils toastUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        initToastUtils();

        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static ToastUtils getToastUtils() {
        return getInstance().toastUtils;
    }

    //region Initialization
    private void initToastUtils() {
        toastUtils = new ToastUtils(this);
    }
    //endregion

    //region DaggerApplication
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
    //endregion
}
