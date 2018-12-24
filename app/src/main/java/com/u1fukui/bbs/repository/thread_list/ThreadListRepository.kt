package com.u1fukui.bbs.repository.thread_list

import com.u1fukui.bbs.model.BbsThread
import kotlinx.coroutines.Deferred

interface ThreadListRepository {

    fun fetchThreadList(
        lastId: Long?,
        pageSize: Int
    ): Deferred<List<BbsThread>>
}
