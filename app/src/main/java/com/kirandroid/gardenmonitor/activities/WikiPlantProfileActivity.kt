package com.kirandroid.gardenmonitor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.viewmodels.WikiPlantProfileViewModel
import kotlinx.android.synthetic.main.activity_plant_profile.*

class WikiPlantProfileActivity : AppCompatActivity() {

    lateinit var wikiPlantProfileViewModel: WikiPlantProfileViewModel

    lateinit var commonName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_profile)

        // intialising ViewModel
        wikiPlantProfileViewModel = ViewModelProvider(this)[WikiPlantProfileViewModel::class.java]


        // retrieving Intent
        val intent = intent

        // calling api to load data
        getWikiPlantData(intent.getStringExtra("scientificName").toString(), intent.getStringExtra("plant_name").toString())


    }

    private fun getWikiPlantData(scientificName: String, commonName: String) {

        wikiPlantProfileViewModel.getWikiPlantProfileData(commonName).observe(this, Observer {

            Glide.with(this).load(it.originalimage.source.toString()).into(plantImage)
            txtPlantCommonName.text = commonName
            txtPlantScientificName.text = scientificName
            txtPlantDescription.text = it.extract.toString()

        })

    }
}