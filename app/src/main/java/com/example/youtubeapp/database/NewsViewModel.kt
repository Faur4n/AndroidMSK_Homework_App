package com.example.youtubeapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.youtubeapp.NewsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRoomRepository

    val allNews : LiveData<List<NewsEntity>>

    init {
        val newsDao = NewsDatabase.getDatabase(application, viewModelScope).NewsDao()
        repository = NewsRoomRepository(newsDao)
        allNews = repository.allNews
    }

    fun replaceNews(news : List<NewsItem>) = viewModelScope.launch(Dispatchers.IO){
        val newsEntity = Converter().toDatabase(news)
        //newsEntity.forEach { repository.insert(it) }
        repository.replaceAll(newsEntity)
    }

    fun getNewsById(){

    }

//    fun insert(news : NewsItem) = viewModelScope.launch(Dispatchers.IO){
//        val converter : Converter = Converter()
//        val newsEntity = converter.toDatabase(news)
//        repository.replaceAll(newsEntity)
//    }


}