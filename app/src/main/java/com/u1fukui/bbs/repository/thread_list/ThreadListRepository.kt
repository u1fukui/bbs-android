package com.u1fukui.bbs.repository.thread_list


import com.u1fukui.bbs.model.ThreadListResponse

import io.reactivex.Single

interface ThreadListRepository {

    fun fetchThreadList(lastId: Long): Single<ThreadListResponse>
}
