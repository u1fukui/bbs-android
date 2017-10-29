package com.u1fukui.bbs.repository


import android.os.SystemClock

import com.u1fukui.bbs.model.ApiResponse
import com.u1fukui.bbs.model.Comment
import com.u1fukui.bbs.model.EmptyResponse
import com.u1fukui.bbs.model.User

import java.util.ArrayList
import java.util.Date

import io.reactivex.Single

class ThreadRepository {

    fun fetchCommentList(threadId: Long): Single<List<Comment>> {
        return Single.create { e ->
            SystemClock.sleep(1000)

            val list = ArrayList<Comment>()
            for (i in 1..20) {
                val author = User(i.toLong(), "コメンター" + i)
                val comment = Comment(i.toLong(), threadId, i, "コメント", author, i, false, Date())
                list.add(comment)
            }
            e.onSuccess(list)
        }
    }

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
