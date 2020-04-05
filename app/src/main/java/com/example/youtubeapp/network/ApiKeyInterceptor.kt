package com.example.youtubeapp.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor private constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithoutApiKey = chain.request()
        val url = requestWithoutApiKey.url
            .newBuilder()
            .addQueryParameter(ApiKeyInterceptor.API_KEY_HEADER_NAME, TOP_STORIES_API_KEY)
            .build()
        val requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
            .url(url)
            .build()
        return chain.proceed(requestWithAttachedApiKey)
    }

    companion object {
        //zwk7C3pjfWSu5HKCmRyaM2kG30AXJlAS
        private const val TOP_STORIES_API_KEY = "zwk7C3pjfWSu5HKCmRyaM2kG30AXJlAS"
        private const val API_KEY_HEADER_NAME = "api-key"
        fun create(): ApiKeyInterceptor {
            return ApiKeyInterceptor()
        }
    }
}