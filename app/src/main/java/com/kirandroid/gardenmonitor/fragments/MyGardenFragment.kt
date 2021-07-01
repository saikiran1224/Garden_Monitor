package com.kirandroid.gardenmonitor.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.activities.ScanPlantsActivity
import com.kirandroid.gardenmonitor.adapters.PlantAdapter
import com.kirandroid.gardenmonitor.adapters.PlantOrganImageAdapter
import com.kirandroid.gardenmonitor.models.PlantData
import kotlinx.android.synthetic.main.activity_manage_plants.*
import kotlinx.android.synthetic.main.fragment_my_garden.*
import kotlinx.android.synthetic.main.fragment_my_garden.plantsRecycler
import kotlinx.android.synthetic.main.fragment_my_garden.view.*

class MyGardenFragment : Fragment() {

    lateinit var plantsRecyclerView: RecyclerView
    lateinit var plantsList: ArrayList<PlantData>

    lateinit var noDataAnim:LottieAnimationView
    lateinit var txtNoData: TextView

    lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_garden, container, false)

        // initialising Firebase and Firestore
        FirebaseApp.initializeApp(requireContext())
        db = Firebase.firestore


        plantsRecyclerView = view.findViewById(R.id.plantsRecycler)
        plantsList = ArrayList()

        noDataAnim = view.findViewById(R.id.noDataAnim)
        txtNoData = view.findViewById(R.id.txtNoData)

        view.scanPlantsIcon.setOnClickListener {
            val intent = Intent(context, ScanPlantsActivity::class.java)
            startActivity(intent)
        }

        // retrieving data and passing to recyclerview
        getPlantData()




        return view
    }

    private fun getPlantData() {

        db.collection("My_Garden").get().addOnSuccessListener {
            result->
              for(document in result) {
                  val plantResultData = document.toObject<PlantData>()
                  plantsList.add(plantResultData)
              }

              if(!plantsList.isEmpty()) {
                  noDataAnim.visibility = View.GONE
                  txtNoData.visibility = View.GONE

                  // initialising adapter
                  val plantOrganImageAdapter = PlantAdapter(requireContext(), plantsList)
                  val gridLayoutManager = GridLayoutManager(requireContext(), 2)
                  plantsRecyclerView.adapter = plantOrganImageAdapter
                  plantsRecyclerView.layoutManager = gridLayoutManager

              } else {
                  // No Data in My Garden
                  noDataAnim.visibility = View.VISIBLE
                  txtNoData.visibility = View.VISIBLE
              }
        }



    }

}