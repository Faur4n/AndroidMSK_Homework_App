package com.example.youtubeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainAdapter(private val context: Context/*, newsFeed: NewsFeed*/) : RecyclerView.Adapter<MainViewHolder>() {

    private var newsList = emptyList<NewsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.news_item_layout,parent,false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.setUi(newsList[position],context)
    }

    fun replaceItems(newsItems: List<NewsItem>) {
        this.newsList = newsItems
        notifyDataSetChanged()
    }

    fun getNewsUrl(position: Int): String {
        return this.newsList[position].url!!
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val categoryTextView = itemView.findViewById(R.id.category_text_view) as TextView
    private val imageView = itemView.findViewById(R.id.image_view) as ImageView
    private val timeTextView = itemView.findViewById(R.id.time_text_view) as TextView
    private val newsTextView = itemView.findViewById(R.id.news_text_view) as TextView
    private val titleTextView = itemView.findViewById(R.id.title_text_view) as TextView

    fun setUi(results: NewsItem,context: Context) {
        val imageUrl = results.imageUrl
        val newsTitle = results.title
        val newsCategory = results.category
        val newsAbstract = results.previewText
        val newsTime = results.date

        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
        categoryTextView.text = newsCategory
        timeTextView.text = newsTime
        newsTextView.text = newsAbstract
        titleTextView.text = newsTitle
    }
}