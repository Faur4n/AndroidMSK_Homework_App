package com.example.youtubeapp.model

import java.io.Serializable

data class NewsItem(
    val uid : String?,
    val title: String?,
    val imageUrl: String?,
    val category: String?,
    val date: String?,
    val previewText : String?,
    val url : String?
) : Serializable {
    companion object {
        @JvmStatic
        fun create(
            uid: String,
            title: String,
            imageUrl: String,
            category: String,
            publishDate: String,
            preview: String,
            url: String
        ): NewsItem {
            return NewsItem(
                uid,
                title,
                imageUrl,
                category,
                publishDate,
                preview,
                url
            )
        }
    }
}