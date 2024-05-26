package com.example.stop_watch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()



        Handler().postDelayed({
            val intent = Intent(this,Lets_Started::class.java)
            startActivity(intent)
            finish()




        },4000)


    }
}