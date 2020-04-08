package com.example.youtubeapp.database

import com.example.youtubeapp.model.NewsItem
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
        return newsEntity.map {
            NewsItem(
                it.uid,
                it.title,
                it.imgUrl,
                it.subsection,
                it.published_date,
                it.preview_text,
                it.url
            )
        }
    }

    fun fromDatabaseSingle(newsEntity: NewsEntity) : NewsItem {
        return NewsItem(
            newsEntity.uid,
            newsEntity.title,
            newsEntity.imgUrl,
            newsEntity.subsection,
            newsEntity.published_date,
            newsEntity.preview_text,
            newsEntity.url
        )
    }

}