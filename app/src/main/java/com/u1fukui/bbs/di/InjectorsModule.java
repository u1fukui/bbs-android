package com.u1fukui.bbs.di;

import com.u1fukui.bbs.di.activity.CreateThreadActivityModule;
import com.u1fukui.bbs.di.activity.MainActivityModule;
import com.u1fukui.bbs.di.activity.ThreadDetailActivityModule;
import com.u1fukui.bbs.di.scope.ActivityScope;
import com.u1fukui.bbs.view.activity.CreateThreadActivity;
import com.u1fukui.bbs.view.activity.MainActivity;
import com.u1fukui.bbs.view.activity.ThreadDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class InjectorsModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainActivityModule.class, MainActivityModule.BindModule.class})
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {CreateThreadActivityModule.class, CreateThreadActivityModule.BindModule.class})
    abstract CreateThreadActivity createThreadActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {ThreadDetailActivityModule.class})
    abstract ThreadDetailActivity threadDetailActivity();
}
