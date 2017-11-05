package com.u1fukui.bbs.ui.main.home

import android.os.Bundle
import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.repository.CategoryThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment

class CategoryThreadListFragment : BaseThreadListFragment() {

    override val repository = CategoryThreadListRepository()

    companion object {

        private val ARG_CATEGORY = "arg.categroy"

        fun newInstance(category: Category) =  CategoryThreadListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CATEGORY, category)
            }
        }
    }
}
