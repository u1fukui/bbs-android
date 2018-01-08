package com.u1fukui.bbs.ui.main

import android.text.format.DateFormat
import android.view.View

import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.ui.BindingModel

class ThreadBindingModel(
        private val navigator: ThreadListNavigator,
        val bbsThread: BbsThread
) : BindingModel {

    val updatedAt : CharSequence? = DateFormat.format(DATE_FORMAT_PATTERN, bbsThread.updatedAt)

    fun onClickThread(view: View) {
        navigator.navigateToThreadDetailPage(bbsThread)
    }

    override fun destroy() {}

    companion object {

        private val DATE_FORMAT_PATTERN = "yyyy/MM/dd(E) kk:mm"
    }
}
