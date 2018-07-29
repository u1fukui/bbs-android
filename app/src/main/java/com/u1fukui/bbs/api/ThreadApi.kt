package com.u1fukui.bbs.api

import com.u1fukui.bbs.model.Comment
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ThreadApi {

    @GET("thread/{threadId}/comments")
    fun fetchCommentList(
        @Path("threadId") threadId: Long,
        @Query("lastId") lastId: Long?,
        @Query("size") pageSize: Int
    ): Deferred<List<Comment>>
}