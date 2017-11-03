package com.u1fukui.bbs.di.activity

import com.u1fukui.bbs.di.fragment.InputThreadInfoFragmentModule
import com.u1fukui.bbs.di.fragment.SelectCategoryFragmentModule
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
        @ContributesAndroidInjector(modules = arrayOf(SelectCategoryFragmentModule::class))
        internal abstract fun selectCategoryFragment(): SelectCategoryFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = arrayOf(InputThreadInfoFragmentModule::class))
        internal abstract fun inputThreadInfoFragment(): InputThreadInfoFragment
    }
}
