package com.u1fukui.bbs.ui.main.mypage

import com.u1fukui.bbs.api.ThreadListApi
import com.u1fukui.bbs.repository.HistoryThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment
import javax.inject.Inject

class HistoryThreadListFragment : BaseThreadListFragment() {

    @Inject
    lateinit var threadListApi: ThreadListApi

    override fun getRepository() = HistoryThreadListRepository(threadListApi)

    companion object {

        fun newInstance() = HistoryThreadListFragment()
    }
}
