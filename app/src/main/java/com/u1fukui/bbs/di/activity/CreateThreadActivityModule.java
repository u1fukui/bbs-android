package com.u1fukui.bbs.di.activity;

import com.u1fukui.bbs.di.fragment.InputThreadInfoFragmentModule;
import com.u1fukui.bbs.di.fragment.SelectCategoryFragmentModule;
import com.u1fukui.bbs.di.scope.FragmentScope;
import com.u1fukui.bbs.view.fragment.InputThreadInfoFragment;
import com.u1fukui.bbs.view.fragment.SelectCategoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public class CreateThreadActivityModule {

    @Module
    public abstract class BindModule {

        @FragmentScope
        @ContributesAndroidInjector(modules = {SelectCategoryFragmentModule.class})
        abstract SelectCategoryFragment selectCategoryFragment();

        @FragmentScope
        @ContributesAndroidInjector(modules = {InputThreadInfoFragmentModule.class})
        abstract InputThreadInfoFragment inputThreadInfoFragment();
    }
}
