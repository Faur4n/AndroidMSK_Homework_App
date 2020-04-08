package com.example.youtubeapp.introScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import com.example.youtubeapp.MainActivity
import com.example.youtubeapp.R
import kotlinx.android.synthetic.main.activity_intro_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val NUM_PAGES = 3

class IntroScreenActivity : AppCompatActivity()  {

    lateinit var pagerAdapter : FragmentStatePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
            if(!pref.getBoolean(APP_LAUNCH,false)){
                //true- show intro
                setContentView(R.layout.activity_intro_screen)

                pagerAdapter = IntroPagerAdapter(supportFragmentManager,NUM_PAGES)
                introViewPager.adapter = pagerAdapter

                val indicator = indicator
                indicator.setViewPager(introViewPager)

                editor.putBoolean(APP_LAUNCH,true)
                editor.apply()


                start_button.setOnClickListener {
                    redirect()
                }
//                CoroutineScope(IO).launch {
//                    redirectWithDelay()
//                }
            }else{
                //false-redirect to main activity
                editor.putBoolean(APP_LAUNCH,false)
                editor.apply()
                redirect()
            }
    }

    private suspend fun redirectWithDelay() {
        delay(5000)
        withContext(Main){
            redirect()
        }
    }

    private fun redirect() {
        val intent = Intent(this@IntroScreenActivity,
            MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        ActivityCompat.finishAffinity(this@IntroScreenActivity)
    }


    companion object {
        private const val PREF = "settings"
        private const val APP_LAUNCH = "appLaunch"
    }
}
