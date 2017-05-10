package com.u1fukui.bbs.di.activity;

import com.u1fukui.bbs.di.fragment.HomeFragmentModule;
import com.u1fukui.bbs.di.scope.FragmentScope;
import com.u1fukui.bbs.view.fragment.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public class MainActivityModule {

    @Module
    public abstract class BindModule {

        @FragmentScope
        @ContributesAndroidInjector(modules = {HomeFragmentModule.class})
        abstract HomeFragment homeFragment();
    }
}
