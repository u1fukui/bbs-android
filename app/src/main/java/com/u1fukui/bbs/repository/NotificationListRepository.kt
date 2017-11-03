package com.u1fukui.bbs.repository


import android.os.SystemClock

import com.u1fukui.bbs.model.Notification

import java.util.ArrayList
import java.util.Date

import javax.inject.Inject

import io.reactivex.Single

class NotificationListRepository @Inject
constructor() {

    fun fetchNotificationList(): Single<List<Notification>> {
        //TODO: API実装
        return Single.create { e ->
            SystemClock.sleep(1000)

            val list = ArrayList<Notification>()
            for (i in 0..9) {
                list.add(Notification(i.toLong(), "http://example.com", "通知です" + i, Date()))
            }
            e.onSuccess(list)
        }
    }
}
