package com.kirandroid.gardenmonitor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.utils.AppPreferences

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIMER = 2500;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppPreferences.init(this)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIMER.toLong())

    }
}