package com.u1fukui.bbs.di.activity

import com.u1fukui.bbs.di.fragment.FragmentModule
import com.u1fukui.bbs.di.fragment.InputThreadInfoFragmentModule
import com.u1fukui.bbs.di.scope.FragmentScope
import com.u1fukui.bbs.ui.creation.thread.InputThreadInfoFragment
import com.u1fukui.bbs.ui.creation.thread.SelectCategoryFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
class CreateThreadActivityModule {

    @Module
    abstract inner class BindModule {

        @FragmentScope
        @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
        internal abstract fun selectCategoryFragment(): SelectCategoryFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class, InputThreadInfoFragmentModule::class))
        internal abstract fun inputThreadInfoFragment(): InputThreadInfoFragment
    }
}
