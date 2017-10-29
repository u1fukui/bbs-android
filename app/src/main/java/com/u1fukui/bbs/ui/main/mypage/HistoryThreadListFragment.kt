package com.u1fukui.bbs.ui.main.mypage

import com.u1fukui.bbs.repository.HistoryThreadListRepository
import com.u1fukui.bbs.repository.ThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment

class HistoryThreadListFragment : BaseThreadListFragment() {

    protected override val repository: ThreadListRepository
        get() = HistoryThreadListRepository()

    companion object {

        fun newInstance(): HistoryThreadListFragment {
            return HistoryThreadListFragment()
        }
    }
}
