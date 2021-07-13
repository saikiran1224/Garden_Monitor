package com.kirandroid.gardenmonitor.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.adapters.WikiPlantAttrAdapter
import com.kirandroid.gardenmonitor.models.PlantAttrData
import com.kirandroid.gardenmonitor.models.PlantData
import com.kirandroid.gardenmonitor.utils.AppPreferences
import com.kirandroid.gardenmonitor.utils.AppUtils
import com.kirandroid.gardenmonitor.viewmodels.WikiPlantProfileViewModel
import kotlinx.android.synthetic.main.activity_plant_profile.*

class WikiPlantProfileActivity : AppCompatActivity() {

    lateinit var wikiPlantProfileViewModel: WikiPlantProfileViewModel

    lateinit var plantAttrList: ArrayList<PlantAttrData>

    lateinit var plantScientificName: String
    lateinit var plantCommonName: String
    lateinit var plantAccuracy: String
    lateinit var plantImageUrl: String

    lateinit var recyclerPlantAttr: RecyclerView
    lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_profile)

        // initialising ViewModel
        wikiPlantProfileViewModel = ViewModelProvider(this)[WikiPlantProfileViewModel::class.java]

        // initialising AppPreferences
        AppPreferences.init(this)

        plantAttrList = ArrayList()
        db = Firebase.firestore

        // retrieving Intent
        val intent = intent
        plantCommonName = intent.getStringExtra("plant_name").toString()
        plantScientificName = intent.getStringExtra("scientific_name").toString()
        plantAccuracy = intent.getStringExtra("accuracy").toString()
        plantImageUrl = intent.getStringExtra("imageUrl").toString()

        recyclerPlantAttr = findViewById(R.id.plantAttrRecycler)

        // calling api to load data
        getWikiPlantData(plantScientificName, plantCommonName,plantAccuracy)

        btnAddPlant.setOnClickListener { addPlantToMyGarden(plantCommonName,plantAccuracy,plantImageUrl)  }

        btnTryAgain.setOnClickListener { sendBackToManagePlants() }

    }

    private fun getWikiPlantData(scientificName: String, commonName: String, accuracy: String) {

        wikiPlantProfileViewModel.getWikiPlantProfileData(scientificName).observe(this, Observer {

            Glide.with(this).load(it.originalimage.source).into(plantImage)
            txtPlantCommonName.text = commonName
            txtPlantScientificName.text = scientificName
            txtPlantDescription.text = it.extract

            loadPlantAttributes(accuracy.toString())

        })

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadPlantAttributes(accuracy: String) {

        // adding Data
        plantAttrList.add(PlantAttrData(resources.getDrawable(R.drawable.accuracy_icon), "Accuracy",accuracy+"%"))
        plantAttrList.add(PlantAttrData(resources.getDrawable(R.drawable.temperature_icon), "Temperature", "15-24°C"))
        plantAttrList.add(PlantAttrData(resources.getDrawable(R.drawable.drop_icon), "Water", "250 ml"))
        plantAttrList.add(PlantAttrData(resources.getDrawable(R.drawable.sun_icon), "Light", "Low"))


        // initialising Grid Layout Manager
        val wikiPlantAttrAdapter = WikiPlantAttrAdapter(this,plantAttrList)
        val gridLayoutManager = GridLayoutManager(this,2)
        recyclerPlantAttr.layoutManager = gridLayoutManager
        recyclerPlantAttr.adapter = wikiPlantAttrAdapter

    }

    private fun addPlantToMyGarden(commonName: String, accuracy: String, imageurl: String) {

        val plantData: PlantData = PlantData(commonName,imageurl,accuracy,
            AppUtils.getCurrentDate(),
            AppUtils.getCurrentTime())

        // deleting data from Firestore
        db.collection("Plant_Organ").get().addOnSuccessListener {
                result ->
            for(document in result) {
                // deleting each document in collection of Plant_Organ
                db.collection("Plant_Organ").document(document.id).delete().addOnFailureListener {
                    AppPreferences.showToast(this,it.localizedMessage)
                }
            }
        }

        // adding Data to My Garden Database
        db.collection("My_Garden")
            .add(plantData)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Success! Plant added to My Garden!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()

            }.addOnFailureListener { error ->
                Toast.makeText(this, "Error adding plant: $error", Toast.LENGTH_LONG).show()
            }
    }

    fun sendBackToManagePlants() {

        val intent = Intent(this, ManagePlantsActivity::class.java)
        intent.putExtra("image_source","tryAgain") // just for case to override intent
        startActivity(intent)
        finish()

    }

}