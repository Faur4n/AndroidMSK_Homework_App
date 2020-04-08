package com.example.youtubeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.youtubeapp.database.NewsViewModel
import com.example.youtubeapp.model.NewsItem
import kotlinx.android.synthetic.main.details_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val UID = "uid"


class DetailsFragment : Fragment() {
    private var uid: String = ""

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(UID)!!
        }

    }

    private fun setUI(newsItem: NewsItem) {

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true);


        nested_title_text_view.text = newsItem.title
        nested_main_text_view.text = newsItem.previewText
        Glide.with(this).load(newsItem.imageUrl).into(collapse_toolbar_image_view)
        nested_time_text_view.text = newsItem.date
        toolbar_layout.title = newsItem.title

        //Toast.makeText(activity,newsItem.title, Toast.LENGTH_LONG).show()

        openUrl.setOnClickListener {

            val webViewFragment = WebViewFragment.newInstance(newsItem.url!!)
            activity!!.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_layout,webViewFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.details_activity,container,false)

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val newsItem = newsViewModel.getById(uid)
            withContext(Dispatchers.Main){
                setUI(newsItem)
            }
        }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(uid: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(UID, uid)
                }
            }
    }
}
