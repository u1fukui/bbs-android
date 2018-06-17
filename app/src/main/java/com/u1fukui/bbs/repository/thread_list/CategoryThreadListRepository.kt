package com.u1fukui.bbs.repository.thread_list

import com.u1fukui.bbs.api.ThreadListApi
import com.u1fukui.bbs.model.BbsThread
import kotlinx.coroutines.experimental.Deferred
import javax.inject.Inject

class CategoryThreadListRepository @Inject constructor(
    private val threadListApi: ThreadListApi,
    private val categoryId: Long
) : ThreadListRepository {

    override fun fetchThreadList(lastId: Long): Deferred<List<BbsThread>> =
        threadListApi.fetchCategoryThreadList(categoryId, lastId)
}
