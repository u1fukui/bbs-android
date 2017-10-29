package com.u1fukui.bbs.ui.main.home

import android.os.Bundle

import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.repository.CategoryThreadListRepository
import com.u1fukui.bbs.repository.ThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment

class CategoryThreadListFragment : BaseThreadListFragment() {

    protected override val repository: ThreadListRepository
        get() = CategoryThreadListRepository()

    companion object {

        val TAG = CategoryThreadListFragment::class.java.simpleName

        private val ARG_CATEGORY = "arg.categroy"

        fun newInstance(category: Category): CategoryThreadListFragment {
            val args = Bundle()
            args.putSerializable(ARG_CATEGORY, category)

            val instance = CategoryThreadListFragment()
            instance.arguments = args
            return instance
        }
    }
}
