package com.example.youtubeapp.network.dto

import com.google.gson.annotations.SerializedName

class MultimediaDTO (
    @SerializedName("url")
    val url: String,
    @SerializedName("type")
    val type: String
    ) {

}