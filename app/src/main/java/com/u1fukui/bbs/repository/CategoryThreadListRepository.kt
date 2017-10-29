package com.u1fukui.bbs.repository


import android.os.SystemClock

import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.model.ThreadListResponse
import com.u1fukui.bbs.model.User

import java.util.ArrayList
import java.util.Date

import io.reactivex.Single

class CategoryThreadListRepository : ThreadListRepository {

    override fun fetchThreadList(lastId: Long): Single<ThreadListResponse> {
        //TODO: APIを使う
        return Single.create { e ->
            SystemClock.sleep(1000)

            val list = ArrayList<BbsThread>()
            for (i in lastId + 1..lastId + 20) {
                val author = User(i, "作者" + i)
                list.add(BbsThread(i, "カテゴリスレッド" + i, author, 0, Date(), Date()))
            }

            e.onSuccess(ThreadListResponse(200, list, lastId >= 100))
        }
    }
}
