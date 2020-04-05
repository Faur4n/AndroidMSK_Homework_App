package com.example.youtubeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.IntentCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class IntroScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
            if(!pref.getBoolean(APP_LAUNCH,false)){
                //true- show intro
                setContentView(R.layout.activity_intro_screen)
                editor.putBoolean(APP_LAUNCH,true)
                editor.apply()
                CoroutineScope(IO).launch {
                    redirectWithDelay()
                }
            }else{
                //false-redirect to main activity
                editor.putBoolean(APP_LAUNCH,false)
                editor.apply()
                redirect()
            }

        setContentView(R.layout.activity_intro_screen)
    }

    private suspend fun redirectWithDelay() {
        delay(1000)
        withContext(Main){
            redirect()
        }
    }

    private fun redirect() {
        val intent = Intent(this@IntroScreenActivity,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        ActivityCompat.finishAffinity(this@IntroScreenActivity)
    }



    companion object {
        private const val PREF = "settings"
        private const val APP_LAUNCH = "appLaunch"
    }
}
