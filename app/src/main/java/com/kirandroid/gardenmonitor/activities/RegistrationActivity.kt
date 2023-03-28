package com.kirandroid.gardenmonitor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    var name: String? = ""
    var emailID: String? = ""
    var password: String? = ""
    var confirmPwd: String? = ""

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        AppPreferences.init(this)

        db = Firebase.firestore

        btnSubmit.setOnClickListener {

            name = editName.text.toString().trim()
            emailID = editEmailID.text.toString().trim()
            password = editPassword.text.toString().trim()
            confirmPwd = editConfirmPwd.text.toString().trim()

            if(name.isNullOrEmpty())
                Toast.makeText(this,"Please Enter Name!",Toast.LENGTH_LONG).show()
            else if (emailID.isNullOrEmpty())
                Toast.makeText(this, "Please enter Email ID",Toast.LENGTH_LONG).show()
            else if (password.isNullOrEmpty())
                Toast.makeText(this, "Please enter Password",Toast.LENGTH_LONG).show()
            else if (confirmPwd.isNullOrEmpty())
                Toast.makeText(this, "Please re-enter your Password",Toast.LENGTH_LONG).show()
            else if (password.equals(confirmPwd))
                Toast.makeText(this, "Password and Confirm Password should be same!",Toast.LENGTH_LONG).show()
            else {

                val userData = com.kirandroid.gardenmonitor.models.UserData(name!!,emailID!!, password!!)
                db.collection("User_Data")
                    .add(userData)
                    .addOnSuccessListener {

                        // Setting Shared Preferences
                        AppPreferences.customerName = name
                        AppPreferences.customerEmail = emailID


                        Toast.makeText(this,"User Registered Successfully!",Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userName",name)
                        startActivity(intent)
                    }
            }
        }


    }
}