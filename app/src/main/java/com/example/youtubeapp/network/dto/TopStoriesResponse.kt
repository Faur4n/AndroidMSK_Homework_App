package com.example.youtubeapp.network.dto

import com.google.gson.annotations.SerializedName

class TopStoriesResponse(
    @SerializedName("status")
    val status : String,

    @SerializedName("results")
    val news : List<NewsDTO>

) {

}