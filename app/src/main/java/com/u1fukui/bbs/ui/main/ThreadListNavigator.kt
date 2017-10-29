package com.u1fukui.bbs.ui.main


import android.app.Activity
import android.content.Intent

import com.u1fukui.bbs.ui.Navigator
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.ui.detail.ThreadDetailActivity

open class ThreadListNavigator(activity: Activity) : Navigator(activity) {

    fun navigateToThreadDetailPage(thread: BbsThread) {
        val intent = ThreadDetailActivity.createIntent(activity, thread)
        activity.startActivity(intent)
    }
}
