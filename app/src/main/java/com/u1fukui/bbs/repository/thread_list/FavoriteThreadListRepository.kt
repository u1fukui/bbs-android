package com.u1fukui.bbs.repository.thread_list

import com.u1fukui.bbs.api.ThreadListApi
import com.u1fukui.bbs.model.BbsThread
import kotlinx.coroutines.Deferred

class FavoriteThreadListRepository constructor(
    private val threadListApi: ThreadListApi
) : ThreadListRepository {

    override fun fetchThreadList(
        lastId: Long?,
        pageSize: Int
    ): Deferred<List<BbsThread>> =
        threadListApi.fetchCategoryThreadList(1, lastId, pageSize)
}
