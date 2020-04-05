package com.example.youtubeapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = arrayOf(NewsEntity::class),version = 1,exportSchema = false)
public abstract class NewsDatabase: RoomDatabase() {

    abstract fun NewsDao() : NewsDAO

    private class NewsDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {

                    //add item onOpen database

                    var newsDao = database.NewsDao()

                    // Delete all content here.
                    newsDao.deleteAll()

                    // Add sample words.
                    val uuid1 = UUID.randomUUID().toString()
                    val uuid2 = UUID.randomUUID().toString()

                    //val oneNews = NewsEntity(uuid,"123","123","123","123","123","")
                    val twoNews = NewsEntity(uuid1,"436","436","436","436","436","")
                    val oneNews = NewsEntity(uuid2,"123","123","123","123","123","")

                    val news = listOf<NewsEntity>(twoNews,oneNews)
                    newsDao.insertAll(news)
//                    news.forEach {
//                        newsDao.insert(it)
//                        Log.i("AAA","itemInsert$it")
//                    }
                    //newsDao.insertAll(news)
                }
            }
        }
    }

    companion object{

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : NewsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                )//.addCallback(NewsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}