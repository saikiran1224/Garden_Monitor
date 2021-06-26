package com.kirandroid.gardenmonitor.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.activities.ScanPlantsActivity
import kotlinx.android.synthetic.main.fragment_my_garden.*
import kotlinx.android.synthetic.main.fragment_my_garden.view.*

class MyGardenFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_garden, container, false)

        view.scanPlantsIcon.setOnClickListener {
            val intent = Intent(context, ScanPlantsActivity::class.java)
            startActivity(intent)
        }


        return view
    }

}