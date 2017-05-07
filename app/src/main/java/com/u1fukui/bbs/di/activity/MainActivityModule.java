package com.u1fukui.bbs.di.activity;

import android.content.Context;

import com.u1fukui.bbs.di.fragment.HomeFragmentModule;
import com.u1fukui.bbs.di.scope.FragmentScope;
import com.u1fukui.bbs.view.activity.MainActivity;
import com.u1fukui.bbs.view.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public class MainActivityModule {

    @Module
    public abstract class BindModule {

        @FragmentScope
        @ContributesAndroidInjector(modules = {HomeFragmentModule.class})
        abstract HomeFragment homeFragment();
    }

    @Provides
    Context provideContext(MainActivity activity) {
        return activity;
    }
}
