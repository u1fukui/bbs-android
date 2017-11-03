package com.u1fukui.bbs.repository


import com.u1fukui.bbs.model.ThreadListResponse

import io.reactivex.Single

interface ThreadListRepository {

    fun fetchThreadList(lastId: Long): Single<ThreadListResponse>
}
