package com.kirandroid.gardenmonitor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kirandroid.gardenmonitor.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btnSendOTP.setOnClickListener {

            if(editPhoneNumber.text!!.length < 10) {
                Toast.makeText(this,"Please enter Valid Phone Number",Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("phoneNumber", editPhoneNumber.text.toString())
                startActivity(intent)
            }

        }
    }
}