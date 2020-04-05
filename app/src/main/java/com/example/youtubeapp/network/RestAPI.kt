package com.example.youtubeapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestAPI {
    private const val TOP_STORIES_BASE_URL = "https://api.nytimes.com/svc/"

    private var topStoriesEndpoint: TopStoriesEndpoint

    init {
        val client: OkHttpClient = buildClient()
        val retrofit : Retrofit = buildRetrofit(client)

        topStoriesEndpoint = retrofit.create(TopStoriesEndpoint::class.java)
    }

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TOP_STORIES_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun buildClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(ApiKeyInterceptor.create())
            .build()
    }

    fun topStories(): TopStoriesEndpoint {
        return topStoriesEndpoint
    }


}