package com.u1fukui.bbs.repository.thread_list

import com.u1fukui.bbs.api.ThreadListApi
import com.u1fukui.bbs.model.BbsThread
import kotlinx.coroutines.experimental.Deferred

class HistoryThreadListRepository constructor(
    private val threadListApi: ThreadListApi
) : ThreadListRepository {

    //TODO: Apply correct API
    override fun fetchThreadList(lastId: Long): Deferred<List<BbsThread>> =
        threadListApi.fetchCategoryThreadList(1)
}
