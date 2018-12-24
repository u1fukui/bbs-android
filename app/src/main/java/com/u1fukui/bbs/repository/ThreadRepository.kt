package com.u1fukui.bbs.repository


import android.os.SystemClock
import com.u1fukui.bbs.api.ThreadApi
import com.u1fukui.bbs.model.ApiResponse
import com.u1fukui.bbs.model.Comment
import com.u1fukui.bbs.model.EmptyResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred

class ThreadRepository(private val threadApi: ThreadApi) {

    fun fetchCommentList(
        threadId: Long,
        lastId: Long?,
        pageSize: Int
    ): Deferred<List<Comment>> =
            threadApi.fetchCommentList(threadId, lastId, pageSize)

    fun postThread(): Single<ApiResponse> {
        return Single.create { e ->
            SystemClock.sleep(1000)
            e.onSuccess(EmptyResponse(200))
        }
    }

    fun postComment(): Single<ApiResponse> {
        return Single.create { e ->
            SystemClock.sleep(1000)
            e.onSuccess(EmptyResponse(200))
        }
    }
}
