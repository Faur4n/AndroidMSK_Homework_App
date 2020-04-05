package com.example.youtubeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDAO {
    @Query("SELECT * from news_table ORDER BY published_date ASC")
    fun getAll() : LiveData<List<NewsEntity>>

    @Query("SELECT * from news_table WHERE uid = :id")
    suspend fun getNewsByID(id: Int) : NewsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news : List<NewsEntity>)
            //= news.forEach {insert(it)}

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news : NewsEntity)

    @Query("Delete from news_table")
    suspend fun deleteAll()

}