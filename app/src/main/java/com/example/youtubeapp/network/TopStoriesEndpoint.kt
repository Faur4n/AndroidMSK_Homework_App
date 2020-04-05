package com.example.youtubeapp.network

import com.example.youtubeapp.network.dto.TopStoriesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TopStoriesEndpoint {
    @GET("topstories/v2/{section}.json")
    operator fun get(@Path("section") section: String): Single<TopStoriesResponse?>?
}