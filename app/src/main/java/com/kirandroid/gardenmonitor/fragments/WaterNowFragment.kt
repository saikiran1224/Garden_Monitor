package com.kirandroid.gardenmonitor.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.adapters.MyGardenPlantsAdapter
import com.kirandroid.gardenmonitor.adapters.WaterNowPlantsAdapter
import com.kirandroid.gardenmonitor.models.PlantData

class WaterNowFragment : Fragment() {

    lateinit var db: FirebaseFirestore

    lateinit var waterNowRecyclerView: RecyclerView
    lateinit var plantsList: ArrayList<PlantData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_water_now, container, false)

        // initialising Firebase and Firestore
        FirebaseApp.initializeApp(requireContext())
        db = Firebase.firestore

        waterNowRecyclerView = view.findViewById(R.id.waterNowRecycler)
        plantsList = ArrayList()

        // retrieving data and passing to recyclerview
        getPlantData()


        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun getPlantData() {

        db.collection("My_Garden").get().addOnSuccessListener {
                result->
            for(document in result) {
                val plantResultData = document.toObject<PlantData>()
                plantsList.add(plantResultData)
            }

            if(!plantsList.isEmpty()) {

                // initialising adapter
                val waterNowAdapter = context?.let { WaterNowPlantsAdapter(it, plantsList) }
                val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                waterNowRecyclerView.adapter = waterNowAdapter
                waterNowRecyclerView.layoutManager = linearLayoutManager

            } else {
                Toast.makeText(context, "No Data Found !",Toast.LENGTH_LONG).show()
            }
        }



    }


}