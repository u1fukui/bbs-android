package com.u1fukui.bbs.ui.detail


import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.ui.creation.comment.CreateCommentActivity
import com.u1fukui.bbs.ui.Navigator

import javax.inject.Inject

class ThreadDetailNavigator @Inject
constructor(activity: ThreadDetailActivity) : Navigator(activity) {

    fun navigateToCreateCommentPage(thread: BbsThread) {
        val intent = CreateCommentActivity.createIntent(activity, thread)
        activity.startActivity(intent)
    }
}
