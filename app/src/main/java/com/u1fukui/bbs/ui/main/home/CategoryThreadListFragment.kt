package com.u1fukui.bbs.ui.main.home

import android.os.Bundle
import com.u1fukui.bbs.api.ThreadListApi
import com.u1fukui.bbs.model.Category
import com.u1fukui.bbs.repository.thread_list.CategoryThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment
import javax.inject.Inject

class CategoryThreadListFragment : BaseThreadListFragment() {

    @Inject
    lateinit var threadListApi: ThreadListApi

    private val category by lazy {
        arguments?.getSerializable(ARG_CATEGORY) as Category
    }

    override fun getRepository() = CategoryThreadListRepository(threadListApi, category.id)

    companion object {

        private val ARG_CATEGORY = "arg.categroy"

        fun newInstance(category: Category) = CategoryThreadListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CATEGORY, category)
            }
        }
    }
}
