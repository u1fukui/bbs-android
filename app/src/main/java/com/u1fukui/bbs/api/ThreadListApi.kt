package com.u1fukui.bbs.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ThreadListApi {

    //TODO: remove
    @GET("search/repositories")
    fun search(@Query("q") query: String): Single<Any>
}