package com.u1fukui.bbs.ui.notification

import android.text.format.DateFormat
import android.view.View

import com.u1fukui.bbs.App
import com.u1fukui.bbs.model.Notification
import com.u1fukui.bbs.ui.ViewModel

class NotificationViewModel(val notification: Notification) : ViewModel {

    val createdAt = DateFormat.format(DATE_FORMAT_PATTERN, notification.createdAt)!!

    fun onClickNotification(view: View) {
        //TODO: 実装
        App.getToastUtils().showToast("click notification " + notification.id)
    }

    override fun destroy() {}

    companion object {

        private val DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm"
    }
}
