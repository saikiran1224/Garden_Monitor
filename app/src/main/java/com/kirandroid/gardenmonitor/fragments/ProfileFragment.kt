package com.kirandroid.gardenmonitor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.models.UserData
import com.kirandroid.gardenmonitor.utils.AppPreferences
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        context?.let { AppPreferences.init(it) }

        db = Firebase.firestore

        db.collection("User_Data").get()
            .addOnSuccessListener {
                result ->
                run {
                    for (document in result) {
                        val userData = document.toObject<UserData>()
                        txtName.text = AppPreferences.customerName
                        txtEmail.text = AppPreferences.customerEmail
                    }
                }
        }


        return view
    }

}