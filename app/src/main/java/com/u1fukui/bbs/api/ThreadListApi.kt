package com.u1fukui.bbs.api

import com.u1fukui.bbs.model.BbsThread
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ThreadListApi {

    @GET("threads")
    fun fetchCategoryThreadList(
        @Query("category") categoryId: Long,
        @Query("lastId") lastId: Long?,
        @Query("size") pageSize: Int
    ): Deferred<List<BbsThread>>
}