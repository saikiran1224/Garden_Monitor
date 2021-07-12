package com.kirandroid.gardenmonitor.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.kirandroid.gardenmonitor.activities.WikiPlantProfileActivity
import com.kirandroid.gardenmonitor.responses.WikiPlantProfileResponse
import com.kirandroid.gardenmonitor.retrofit.ApiService
import com.kirandroid.gardenmonitor.retrofit.RetrofitUtils
import com.kirandroid.gardenmonitor.retrofit.WikiService
import com.kirandroid.gardenmonitor.utils.AppPreferences
import com.kirandroid.gardenmonitor.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class WikiPlantProfileViewModel: ViewModel() {

    // When this data changes, it triggers the UI to do an update
    // We are going to access data from Datamodel and going to post the value to MutableLivedata which
    // triggers the observer in the HomeFragment where it updates the data

    // Everytime we post data the observer will get triggered

    private val wikiPlantProfileData = MutableLiveData<WikiPlantProfileResponse>()

    // accessing the data received from call and posting the data to LiveData
   fun getWikiPlantProfileData(plantCommonName: String): MutableLiveData<WikiPlantProfileResponse> {

        val wikiService: WikiService? = RetrofitUtils.getInstance(AppUtils.wikiUrl)!!.create(WikiService::class.java)

        val response: Call<WikiPlantProfileResponse?>? = wikiService?.WikiPlantProfileResponse(plantCommonName)

        response!!.enqueue(object : Callback<WikiPlantProfileResponse?> {
            override fun onResponse(call: Call<WikiPlantProfileResponse?>, response: Response<WikiPlantProfileResponse?>) {

                if (response.isSuccessful() && response.body() != null) {
                    val res = response.body()
                    wikiPlantProfileData.postValue(res)
                } else {
                    Log.d("Res","Inside " + wikiPlantProfileData)
                }

            }
            override fun onFailure(call: Call<WikiPlantProfileResponse?>, t: Throwable) {
                wikiPlantProfileData.postValue(null)
                Log.d("Res","Outside" + t.message)
            }
        })

        return wikiPlantProfileData
    }



}