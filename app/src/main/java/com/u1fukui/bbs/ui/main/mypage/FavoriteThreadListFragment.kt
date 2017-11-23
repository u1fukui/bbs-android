package com.u1fukui.bbs.ui.main.mypage

import com.u1fukui.bbs.repository.FavoriteThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment

class FavoriteThreadListFragment : BaseThreadListFragment() {

    override fun getRepository() = FavoriteThreadListRepository()

    companion object {

        @JvmStatic
        fun newInstance() = FavoriteThreadListFragment()
    }

}
