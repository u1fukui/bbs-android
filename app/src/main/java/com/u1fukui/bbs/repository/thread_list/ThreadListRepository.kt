package com.u1fukui.bbs.repository.thread_list

import com.u1fukui.bbs.model.BbsThread
import kotlinx.coroutines.experimental.Deferred

interface ThreadListRepository {

    fun fetchThreadList(lastId: Long): Deferred<List<BbsThread>>
}
