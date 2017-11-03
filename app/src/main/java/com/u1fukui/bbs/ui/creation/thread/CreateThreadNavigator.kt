package com.u1fukui.bbs.ui.creation.thread


import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.ui.Navigator

import javax.inject.Inject

class CreateThreadNavigator @Inject
constructor(activity: CreateThreadActivity) : Navigator(activity) {

    fun navigateToInputInfoPage(category: Category) {
        if (activity is CreateThreadActivity) {
            val activity = activity
            activity.supportFragmentManager.beginTransaction()
                    .replace(activity.fragmentContainerId, InputThreadInfoFragment.newInstance(category))
                    .addToBackStack(null)
                    .commit()
        }
    }
}
