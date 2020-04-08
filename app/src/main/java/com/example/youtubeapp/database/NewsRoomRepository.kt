package com.example.youtubeapp.database

import androidx.lifecycle.LiveData

class NewsRoomRepository(private val newsDAO: NewsDAO){

    val allNews : LiveData<List<NewsEntity>> = newsDAO.getAll()
    suspend fun replaceAll(news : List<NewsEntity>){
        newsDAO.deleteAll()
        newsDAO.insertAll(news)
    }

    suspend fun insert(oneNews : NewsEntity){
        newsDAO.insert(oneNews)
    }

    suspend fun getNewsById(id: String) : NewsEntity{
        return newsDAO.getNewsByID(id)
    }
}