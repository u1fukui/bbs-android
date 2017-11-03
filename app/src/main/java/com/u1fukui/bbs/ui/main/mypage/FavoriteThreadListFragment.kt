package com.u1fukui.bbs.ui.main.mypage

import com.u1fukui.bbs.repository.FavoriteThreadListRepository
import com.u1fukui.bbs.repository.ThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment

class FavoriteThreadListFragment : BaseThreadListFragment() {

    protected override val repository: ThreadListRepository
        get() = FavoriteThreadListRepository()

    companion object {

        @JvmStatic
        fun newInstance(): FavoriteThreadListFragment {
            return FavoriteThreadListFragment()
        }
    }

}
