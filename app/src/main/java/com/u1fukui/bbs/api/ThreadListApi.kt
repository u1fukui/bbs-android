package com.u1fukui.bbs.api

import com.u1fukui.bbs.model.BbsThread
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ThreadListApi {

    @GET("threads")
    fun fetchCategoryThreadList(
        @Query("category") categoryId: Long,
        @Query("lastId") lastId: Long = 0
    ): Deferred<List<BbsThread>>
}