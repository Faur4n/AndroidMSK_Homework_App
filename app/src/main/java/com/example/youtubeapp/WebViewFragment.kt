package com.example.youtubeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.details_activity.toolbar
import kotlinx.android.synthetic.main.fragment_web_view.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val NEWS_URL = "news_utrl"

class WebViewFragment : Fragment() {
    private var newsUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsUrl = it.getString(NEWS_URL)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_web_view,container,false)

        return view
    }

    override fun onStart() {
        super.onStart()

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val progressBar = progressBar_web_view
        val txtview = textView_web_view
        webView.loadUrl(newsUrl)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100 && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                    txtview.visibility = View.VISIBLE
                }
                progressBar.progress = progress
                txtview.text = progress.toString() +" %"
                if (progress == 100) {
                    progressBar.visibility = ProgressBar.GONE
                    txtview.visibility = View.GONE
                }
            }
        }



    }

    companion object {

        @JvmStatic
        fun newInstance(newsUrl: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(NEWS_URL, newsUrl)
                }
            }
    }
}
