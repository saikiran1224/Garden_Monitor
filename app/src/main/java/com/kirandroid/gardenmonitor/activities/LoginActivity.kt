package com.kirandroid.gardenmonitor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kirandroid.gardenmonitor.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btnSendOTP.setOnClickListener {
            val intent = Intent(this, OTPActivity::class.java)

            startActivity(intent)
        }
    }
}