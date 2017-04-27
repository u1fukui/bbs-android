package com.u1fukui.bbs;


import android.app.Application;

import com.u1fukui.bbs.utils.ToastUtils;

import lombok.Getter;

public class App extends Application {

    private static App instance;

    @Getter
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

    private void initToastUtils() {
        toastUtils = new ToastUtils(this);
    }
}
