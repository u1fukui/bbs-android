package com.u1fukui.bbs.utils


import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import com.u1fukui.bbs.network.ApiErrorMesageResolver

class ToastUtils(private val context: Context) {

    private var toast: Toast? = null

    fun showToast(@StringRes messageId: Int) {
        showToast(context.getString(messageId))
    }

    fun showApiError(throwable: Throwable) {
        showToast(ApiErrorMesageResolver.getMessage(throwable))
    }

    fun showToast(message: CharSequence) {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast!!.show()
    }
}
