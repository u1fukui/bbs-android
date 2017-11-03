package com.u1fukui.bbs.di

import com.u1fukui.bbs.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AppModule::class, InjectorsModule::class))
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {

        abstract fun appModule(activityModule: AppModule)

        override fun seedInstance(instance: App) {
            appModule(AppModule(instance))
        }
    }
}
