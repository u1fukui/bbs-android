package com.u1fukui.bbs.ui.creation.thread

import android.view.View

import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.ui.ViewModel

class CategoryViewModel(
        val category: Category,
        private val navigator: CreateThreadNavigator
) : ViewModel {

    fun onClickCategory(view: View) {
        navigator.navigateToInputInfoPage(category)
    }

    override fun destroy() {}
}
