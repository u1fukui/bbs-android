package com.u1fukui.bbs.ui


import android.app.Activity

import javax.inject.Inject

open class Navigator @Inject
constructor(protected val activity: Activity) {

    fun finish() {
        activity.finish()
    }
}
