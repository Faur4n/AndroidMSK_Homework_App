package com.example.youtubeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class DetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val webView = findViewById<WebView>(R.id.webView)
        val url = intent.getStringExtra(EXTRA_NEWS_ITEM)
        webView.loadUrl(url)
    }




    companion object{

        private val EXTRA_NEWS_ITEM = "extra:newsItem"

        fun start(context: Context, url: String) {
            context.startActivity(
                Intent(context, DetailsActivity::class.java).putExtra(
                    EXTRA_NEWS_ITEM,
                    url
                )
            )
        }
    }
}
