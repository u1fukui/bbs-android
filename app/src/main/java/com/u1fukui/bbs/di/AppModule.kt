package com.u1fukui.bbs.di

import android.content.Context
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.u1fukui.bbs.App
import com.u1fukui.bbs.R
import com.u1fukui.bbs.repository.ThreadRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideContext(): Context = app

    @Provides
    fun provideThreadRepository() = ThreadRepository()

    @Provides @Singleton
    fun provideMoshi() =
            Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

    @Provides @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi) =
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(app.getString(R.string.api_base))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
}
