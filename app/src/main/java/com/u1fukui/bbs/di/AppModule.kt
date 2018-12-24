package com.u1fukui.bbs.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.u1fukui.bbs.App
import com.u1fukui.bbs.BuildConfig
import com.u1fukui.bbs.R
import com.u1fukui.bbs.api.ThreadApi
import com.u1fukui.bbs.api.ThreadListApi
import com.u1fukui.bbs.model.BbsThread
import com.u1fukui.bbs.model.Comment
import com.u1fukui.bbs.model.User
import com.u1fukui.bbs.network.ApplicationJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideMoshi() =
        Moshi.Builder()
            .add(ApplicationJsonAdapterFactory.INSTANCE)
            .build()

    @Provides
    @Singleton
    fun providesOkHttp() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi) =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(app.getString(R.string.api_base))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideThreadListApi(retrofit: Retrofit): ThreadListApi {
        //        retrofit.create(ThreadListApi::class.java)

        //TODO: Remove
        return object : ThreadListApi {
            override fun fetchCategoryThreadList(
                categoryId: Long,
                lastId: Long?,
                pageSize: Int
            ): Deferred<List<BbsThread>> =
                GlobalScope.async {
                    val key = lastId ?: 0
                    createThreadDebugList(key, pageSize)
                }
        }
    }

    private fun createThreadDebugList(lastId: Long, pageSize: Int): List<BbsThread> =
        ((lastId + 1)..(lastId + pageSize)).map {
            BbsThread(
                it,
                "お気に入りスレッド$it",
                User(it, "作者$it"),
                0,
                Date(),
                Date()
            )
        }

    @Provides
    @Singleton
    fun provideThreadApi(retrofit: Retrofit): ThreadApi {
        //        retrofit.create(ThreadApi::class.java)

        //TODO: Remove
        return object : ThreadApi {
            override fun fetchCommentList(
                threadId: Long,
                lastId: Long?,
                pageSize: Int
            ): Deferred<List<Comment>> =
                GlobalScope.async(Dispatchers.Default) {
                    val key = lastId ?: 0
                    createCommentDebugList(threadId, key, pageSize)
                }
        }
    }

    private fun createCommentDebugList(
        threadId: Long,
        lastId: Long,
        pageSize: Int
    ): List<Comment> =
        ((lastId + 1)..(lastId + pageSize)).map {
            val author = User(it, "コメンター" + it)
            Comment(
                it,
                threadId,
                it.toInt(),
                "コメント",
                author,
                it.toInt(),
                false,
                Date()
            )
        }
}
