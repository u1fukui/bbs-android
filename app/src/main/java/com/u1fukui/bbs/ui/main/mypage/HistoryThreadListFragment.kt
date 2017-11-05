package com.u1fukui.bbs.ui.main.mypage

import com.u1fukui.bbs.repository.HistoryThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment

class HistoryThreadListFragment : BaseThreadListFragment() {

    override val repository = HistoryThreadListRepository()

    companion object {

        @JvmStatic
        fun newInstance() = HistoryThreadListFragment()
    }
}
