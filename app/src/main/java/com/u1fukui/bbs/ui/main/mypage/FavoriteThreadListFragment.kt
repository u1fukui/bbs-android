package com.u1fukui.bbs.ui.main.mypage

import com.u1fukui.bbs.api.ThreadListApi
import com.u1fukui.bbs.repository.FavoriteThreadListRepository
import com.u1fukui.bbs.ui.main.BaseThreadListFragment
import javax.inject.Inject

class FavoriteThreadListFragment : BaseThreadListFragment() {

    @Inject
    lateinit var threadListApi: ThreadListApi

    override fun getRepository() = FavoriteThreadListRepository(threadListApi)

    companion object {

        fun newInstance() = FavoriteThreadListFragment()
    }

}
