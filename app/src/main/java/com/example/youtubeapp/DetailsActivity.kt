package com.example.youtubeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.youtubeapp.database.NewsViewModel
import kotlinx.android.synthetic.main.activity_intro_screen.*
import kotlinx.android.synthetic.main.details_activity.*
import kotlinx.android.synthetic.main.details_fragment_host.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailsActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_fragment_host)

//        setSupportActionBar(toolbar_host)
//
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true);

//        val webView = findViewById<WebView>(R.id.webView)
//        val url = intent.getStringExtra(EXTRA_NEWS_ITEM)
//        webView.loadUrl(url)

        val uid = intent.getStringExtra(EXTRA_NEWS_ITEM)


        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(R.id.frame_layout,DetailsFragment.newInstance(uid))
            .commit()

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
