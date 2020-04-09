package com.example.youtubeapp


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.youtubeapp.database.Converter
import com.example.youtubeapp.database.NewsDatabase
import com.example.youtubeapp.database.NewsRoomRepository
import com.example.youtubeapp.model.NewsItem
import com.example.youtubeapp.network.RestAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class UpdateService : Service() {


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
                val category = intent?.getStringExtra("category")
                val  notificationManager =
                    getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.notify(1, issueNotification())

        updateNews(category!!)

        return START_STICKY
    }


    private val repository: NewsRoomRepository
    private var disposable : Disposable? = null

    init {
        val newsDao = NewsDatabase.getDatabase(this@UpdateService, CoroutineScope(IO)).NewsDao()
        repository = NewsRoomRepository(newsDao)
    }


    private fun updateNews(category : String){
        disposable  = RestAPI
            .topStories()[category]
            ?.map { response -> TopStoriesMapper.map(response.news) }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(this::setupNews,this::handleError) ?: throw NullPointerException();
    }

    private fun setupNews(newsItems: List<NewsItem>){

        val kek = updateDb(newsItems)
        kek.invokeOnCompletion {
                stopSelf()
        }
        //notificationManager.cancelAll()

    }

    private fun updateDb(newsItems: List<NewsItem>) = CoroutineScope(IO).launch{
        val newsEntity = Converter().toDatabase(newsItems)
        repository.replaceAll(newsEntity)


    }

    private fun handleError(th: Throwable){
        Toast.makeText(this@UpdateService,"Error HandleERROR", Toast.LENGTH_LONG).show()
        stopSelf()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun makeNotificationChannel(id : String,  name : String,  importance : Int)
    {
        val channel = NotificationChannel(id, name, importance);
        channel.setShowBadge(true); // set false to disable badges, Oreo exclusive

        val notificationManager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel);
    }

    private fun issueNotification() : Notification?
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


        return notification.build()
    }

    override fun onDestroy() {
        if (!disposable!!.isDisposed) {
                disposable!!.dispose()
        }
        val  notificationManager =
            getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()

    }



}