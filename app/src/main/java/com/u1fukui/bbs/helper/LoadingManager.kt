package com.u1fukui.bbs.helper


import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.u1fukui.bbs.network.ApiErrorMesageResolver

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
        showErrorView(ApiErrorMesageResolver.getMessage(t))
    }

    fun showErrorView(message: String) {
        contentVisibility.set(View.GONE)

        errorMessage.set(message)
        errorViewVisibility.set(View.VISIBLE)

        finishLoading()
    }
}
