package com.example.youtubeapp.database

import com.example.youtubeapp.NewsItem
import java.util.*

class Converter() {

    fun toDatabase(newsItem: List<NewsItem>) : List<NewsEntity> {

        return newsItem.map { NewsEntity(UUID.randomUUID().toString(),
            it.category,
            it.title,
            it.previewText,
            it.date,
            it.url,
            it.imageUrl
        ) }
    }

    fun fromDatabase(newsEntity: List<NewsEntity>) : List<NewsItem> {
        return newsEntity.map {NewsItem(
            it.uid,
            it.title,
            it.imgUrl,
            it.subsection,
            it.published_date,
            it.preview_text,
            it.url
            ) }
    }

}