package com.u1fukui.bbs.repository


import com.u1fukui.bbs.api.ThreadListApi
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.model.ThreadListResponse
import com.u1fukui.bbs.model.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class CategoryThreadListRepository @Inject constructor(
        private val threadListApi: ThreadListApi,
        private val categoryId: Int
) : ThreadListRepository {

    override fun fetchThreadList(lastId: Long): Single<ThreadListResponse> =
            threadListApi.search("android")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {
                        ThreadListResponse(0,
                                createDebugDataList(lastId),
                                lastId >= 100)
                    }

    private fun createDebugDataList(lastId: Long): List<BbsThread> =
            ((lastId + 1)..(lastId + 20)).map {
                BbsThread(
                        it.toLong(),
                        "カテゴリスレッド$it",
                        User(it.toLong(), "作者$it"),
                        0,
                        Date(),
                        Date()
                )
            }
}