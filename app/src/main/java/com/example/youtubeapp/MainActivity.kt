package com.example.youtubeapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeapp.database.Converter
import com.example.youtubeapp.database.NewsViewModel
import com.example.youtubeapp.model.NewsItem
import com.example.youtubeapp.network.NewsCategory
import com.example.youtubeapp.network.RestAPI
import com.example.youtubeapp.news_recycler.CategoriesSpinnerAdapter
import com.example.youtubeapp.news_recycler.MainAdapter
import com.example.youtubeapp.news_recycler.NewsItemDecoration
import com.example.youtubeapp.news_recycler.RecyclerItemClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit var newsFeed : NewsFeed
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var reloadButton: ImageButton
    lateinit var toolbar: Toolbar

    private lateinit var newsViewModel: NewsViewModel


    lateinit var spinnerCategories: Spinner


    private  var categoriesAdapter: CategoriesSpinnerAdapter? = null

    private var newsAdapter: MainAdapter? = null


    private val compositeDisposable: CompositeDisposable? = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        spinnerCategories = findViewById<Spinner>(R.id.spinner)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        setupSpinner()

        val newsList = ArrayList<Int>()
        for(i in 0 until 30){
            newsList.add(i)
        }


        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.error_textView)
        reloadButton = findViewById(R.id.reload_button)


        /* Setup recycler view*/
        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            NewsItemDecoration(
                resources.getDimensionPixelSize(R.dimen.spacing_micro)
            )
        )

        newsAdapter = MainAdapter(this)

        recyclerView.adapter = newsAdapter

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val columnsCount = 2
            recyclerView.layoutManager = GridLayoutManager(this, columnsCount)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        //////////////
        //SetViewModel
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        //Set observe to live data


        newsViewModel.allNews.observe(this, Observer {
                news -> news?.let { updateItems(Converter().fromDatabase(it)) }
        })

        reloadButton.setOnClickListener {
            //loadItems(categoriesAdapter!!.selectedCategory.serverValue())
            val serviceIntent = Intent(this,UpdateService::class.java)
            serviceIntent.putExtra("category",categoriesAdapter!!.selectedCategory.serverValue())

            startService(serviceIntent)
        }

        fab.setOnClickListener {
            //loadItems(categoriesAdapter!!.selectedCategory.serverValue())
            //DownloadingNews(this).updateNews(categoriesAdapter!!.selectedCategory.serverValue())

            val serviceIntent = Intent(this,UpdateService::class.java)
            serviceIntent.putExtra("category",categoriesAdapter!!.selectedCategory.serverValue())

            startService(serviceIntent)

            //issueNotification()


        }
        //!!!!!!!!!!!!!!!!!!!! тут грузит еще раз при выборе категории

        var lastSelectedCategory = categoriesAdapter!!.selectedCategory.serverValue()

        categoriesAdapter?.setOnCategorySelectedListener({ category -> if(category.serverValue() == lastSelectedCategory){
            lastSelectedCategory = category.serverValue()
        }else{
            //loadItems(categoriesAdapter!!.selectedCategory.serverValue())
            lastSelectedCategory = category.serverValue()

            val serviceIntent = Intent(this,UpdateService::class.java)
            serviceIntent.putExtra("category",categoriesAdapter!!.selectedCategory.serverValue())

            startService(serviceIntent)
        }
        },spinnerCategories)


        //////End setup

        //Setup UpdateService


        /////
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                recyclerView,
                object :
                    RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        DetailsActivity.start(this@MainActivity, newsAdapter!!.getNewsId(position))
                    }
                })
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun makeNotificationChannel(id : String,  name : String,  importance : Int)
    {
    val channel = NotificationChannel(id, name, importance);
    channel.setShowBadge(true); // set false to disable badges, Oreo exclusive

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.createNotificationChannel(channel);
}

    fun issueNotification()
{

    // make the channel. The method has been discussed before.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        makeNotificationChannel("CHANNEL_1", "Example channel", NotificationManager.IMPORTANCE_DEFAULT);
    }
    // the check ensures that the channel will only be made
    // if the device is running Android 8+

    val notification =
                NotificationCompat.Builder(this, "CHANNEL_1");
    // the second parameter is the channel id.
    // it should be the same as passed to the makeNotificationChannel() method

    notification.setSmallIcon(R.mipmap.ic_launcher) // can use any other icon
        .setContentTitle("Notification!")
        .setContentText("This is an Oreo notification!")
        .setNumber(3).setAutoCancel(true)
        // this shows a number in the notification dots

    val  notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.notify(1, notification.build());
}

    private fun setupSpinner() {
        val categories: Array<NewsCategory> = NewsCategory.values()
        categoriesAdapter = CategoriesSpinnerAdapter.createDefault(this, categories)
        spinnerCategories.adapter = categoriesAdapter
    }

    private fun loadItems(category: String){
        recyclerView.visibility = View.GONE
        reloadButton.visibility = View.GONE
        errorTextView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE


        val disposable : Disposable = RestAPI
            .topStories()[category]
            ?.map { response -> TopStoriesMapper.map(response.news) }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(this::setupNews,this::handleError) ?: throw NullPointerException();
        compositeDisposable?.add(disposable )
    }

    private fun setupNews(newsItems: List<NewsItem>) {
        updateUI()
        //update db
        newsViewModel.replaceNews(newsItems)

        //updateItems(newsItems)
    }

    private fun updateItems(newsItems: List<NewsItem>) {
        updateUI()

        newsAdapter?.replaceItems(newsItems)
    }

    private fun handleError(th: Throwable) {
        reloadButton.visibility = View.VISIBLE
        errorTextView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        Toast.makeText(this,"Error HandleERROR",Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        //loadItems(categoriesAdapter!!.getSelectedCategory().serverValue())
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable!!.clear()
    }

    private fun updateUI(){
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    companion object {
        private const val CATEGORY = "arts"
    }
}

 /*   private fun fetchJson( context: Context) {
        val url = "https://api.nytimes.com/svc/topstories/v2/arts.json?api-key=zwk7C3pjfWSu5HKCmRyaM2kG30AXJlAS"
        //https://api.nytimes.com/svc/topstories/v2/arts.json?api-key=zwk7C3pjfWSu5HKCmRyaM2kG30AXJlAS
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute response")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                newsFeed = gson.fromJson(body,NewsFeed::class.java)

                runOnUiThread {
                    updateUI()
                    recyclerView.adapter = MainAdapter(context,newsFeed)
                }

            }

        })
    }*/



