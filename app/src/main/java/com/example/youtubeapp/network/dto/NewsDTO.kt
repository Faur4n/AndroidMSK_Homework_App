package com.example.youtubeapp.network.dto

import com.google.gson.annotations.SerializedName

class NewsDTO (
    @SerializedName("section")
    val subsection: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("abstract")
    val abstract: String,
    @SerializedName("published_date")
    val published_date: String,
    @SerializedName("url")
    val url:String,
    @SerializedName("multimedia")
    val multimedia: List<MultimediaDTO>)
{
}