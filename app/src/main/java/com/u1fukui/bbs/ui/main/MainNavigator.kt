package com.u1fukui.bbs.ui.main


import android.content.Intent

import com.u1fukui.bbs.ui.creation.thread.CreateThreadActivity
import com.u1fukui.bbs.ui.notification.NotificationListActivity

import javax.inject.Inject

class MainNavigator @Inject
constructor(activity: MainActivity) : ThreadListNavigator(activity) {

    fun navigateToCreateThreadPage() {
        val intent = Intent(activity, CreateThreadActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToNotificationPage() {
        val intent = Intent(activity, NotificationListActivity::class.java)
        activity.startActivity(intent)
    }
}
