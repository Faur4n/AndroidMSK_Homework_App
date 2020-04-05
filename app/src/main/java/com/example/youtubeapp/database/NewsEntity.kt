package com.example.youtubeapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey
    var uid: String = "",
    @ColumnInfo(name = "subsection")
    var subsection: String? = "",
    @ColumnInfo(name = "title")
    var title: String? = "" ,
    @ColumnInfo(name = "preview_text")
    var preview_text: String? = "" ,
    @ColumnInfo(name = "published_date")
    var published_date: String? = "",
    @ColumnInfo(name = "url")
    var url:String? = "",
    @ColumnInfo(name = "imgUrl")
    var imgUrl: String?= ""
)