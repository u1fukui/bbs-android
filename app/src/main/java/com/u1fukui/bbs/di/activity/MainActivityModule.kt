package com.u1fukui.bbs.di.activity

import com.u1fukui.bbs.di.fragment.CategoryThreadListFragmentModule
import com.u1fukui.bbs.di.fragment.FavoriteThreadListFragmentModule
import com.u1fukui.bbs.di.fragment.HistoryThreadListFragmentModule
import com.u1fukui.bbs.di.fragment.HomeFragmentModule
import com.u1fukui.bbs.di.scope.FragmentScope
import com.u1fukui.bbs.ui.main.home.CategoryThreadListFragment
import com.u1fukui.bbs.ui.main.home.HomeFragment
import com.u1fukui.bbs.ui.main.mypage.FavoriteThreadListFragment
import com.u1fukui.bbs.ui.main.mypage.HistoryThreadListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
class MainActivityModule {

    @Module
    abstract inner class BindModule {

        @FragmentScope
        @ContributesAndroidInjector(modules = arrayOf(HomeFragmentModule::class))
        internal abstract fun homeFragment(): HomeFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = arrayOf(CategoryThreadListFragmentModule::class))
        internal abstract fun categoryThreadListFragemnt(): CategoryThreadListFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = arrayOf(FavoriteThreadListFragmentModule::class))
        internal abstract fun favoriteThreadListFragment(): FavoriteThreadListFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = arrayOf(HistoryThreadListFragmentModule::class))
        internal abstract fun historyThreadListFragment(): HistoryThreadListFragment
    }
}
