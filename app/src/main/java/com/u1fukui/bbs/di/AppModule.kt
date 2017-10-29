package com.u1fukui.bbs.di

import android.content.Context

import com.u1fukui.bbs.App
import com.u1fukui.bbs.repository.ThreadRepository

import dagger.Module
import dagger.Provides

@Module
class AppModule(app: App) {

    private val context: Context

    init {
        context = app
    }

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideThreadRepository(): ThreadRepository {
        return ThreadRepository()
    }
}
