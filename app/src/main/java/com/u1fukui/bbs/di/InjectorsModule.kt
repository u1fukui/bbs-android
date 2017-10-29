package com.u1fukui.bbs.di

import com.u1fukui.bbs.di.activity.*
import com.u1fukui.bbs.di.scope.ActivityScope
import com.u1fukui.bbs.ui.creation.comment.CreateCommentActivity
import com.u1fukui.bbs.ui.creation.thread.CreateThreadActivity
import com.u1fukui.bbs.ui.detail.ThreadDetailActivity
import com.u1fukui.bbs.ui.main.MainActivity
import com.u1fukui.bbs.ui.notification.NotificationListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class InjectorsModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, MainActivityModule.BindModule::class))
    internal abstract fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(CreateThreadActivityModule::class, CreateThreadActivityModule.BindModule::class))
    internal abstract fun createThreadActivity(): CreateThreadActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(ThreadDetailActivityModule::class))
    internal abstract fun threadDetailActivity(): ThreadDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(CreateCommentActivityModule::class))
    internal abstract fun createCommentActivity(): CreateCommentActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(NotificationListActivityModule::class))
    internal abstract fun notificationListActivity(): NotificationListActivity
}
