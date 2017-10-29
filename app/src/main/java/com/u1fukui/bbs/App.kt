package com.u1fukui.bbs


import com.u1fukui.bbs.di.DaggerAppComponent
import com.u1fukui.bbs.utils.ToastUtils

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    private var toastUtils: ToastUtils? = null

    override fun onCreate() {
        super.onCreate()
        initToastUtils()

        instance = this
    }

    //region Initialization
    private fun initToastUtils() {
        toastUtils = ToastUtils(this)
    }
    //endregion

    //region DaggerApplication
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    companion object {

        @JvmStatic var instance: App? = null
            private set

        @JvmStatic fun getToastUtils(): ToastUtils? {
            return instance!!.toastUtils
        }
    }
    //endregion
}
