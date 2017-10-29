package com.u1fukui.bbs.helper


import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View

class LoadingManager {

    val contentVisibility = ObservableInt(View.GONE)

    val loadingVisibility = ObservableInt(View.GONE)

    val errorViewVisibility = ObservableInt(View.GONE)

    val errorMessage = ObservableField<String>()

    val isLoading: Boolean
        get() = loadingVisibility.get() == View.VISIBLE

    fun startLoading() {
        loadingVisibility.set(View.VISIBLE)
    }

    fun finishLoading() {
        loadingVisibility.set(View.GONE)
    }

    fun showContentView() {
        errorViewVisibility.set(View.GONE)
        contentVisibility.set(View.VISIBLE)
        finishLoading()
    }

    fun showErrorView(t: Throwable) {
        contentVisibility.set(View.GONE)

        //TODO: エラー文言
        errorMessage.set("エラーです")
        errorViewVisibility.set(View.VISIBLE)

        finishLoading()
    }
}
