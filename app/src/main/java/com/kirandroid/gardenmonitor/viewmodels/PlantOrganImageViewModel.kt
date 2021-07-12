package com.kirandroid.gardenmonitor.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.kirandroid.gardenmonitor.responses.PlantIdentificationResponse
import com.kirandroid.gardenmonitor.retrofit.ApiService
import com.kirandroid.gardenmonitor.retrofit.RetrofitUtils
import com.kirandroid.gardenmonitor.utils.AppPreferences
import com.kirandroid.gardenmonitor.utils.AppUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantOrganImageViewModel: ViewModel() {

    // When this data changes, it triggers the UI to do an update
    // We are going to access data from Datamodel and going to post the value to MutableLivedata which
    // triggers the observer in the HomeFragment where it updates the data

    // Everytime we post data the observer will get triggered

    private val plantIdentificationDataList = MutableLiveData<PlantIdentificationResponse>()

    // accessing the data received from call and posting the data to LiveData
   fun getPlantIdentificationData(images: ArrayList<String>, organs: ArrayList<String>): MutableLiveData<PlantIdentificationResponse> {

        val apiService: ApiService? = RetrofitUtils.getInstance(AppUtils.plantNetUrl)!!.create(ApiService::class.java)
        val response: Call<PlantIdentificationResponse?>? = apiService?.PlantIdentificationResponse(images,organs,true,"en", AppUtils.plantNetApiKey)

        response!!.enqueue(object : Callback<PlantIdentificationResponse?> {
            override fun onResponse(call: Call<PlantIdentificationResponse?>, response: Response<PlantIdentificationResponse?>) {

                if (response.isSuccessful() && response.body() != null) {
                    val res = response.body()
                    plantIdentificationDataList.postValue(res)

                } else {
                    Log.d("Res","Inside " + plantIdentificationDataList)

                }

            }
            override fun onFailure(call: Call<PlantIdentificationResponse?>, t: Throwable) {
                plantIdentificationDataList.postValue(null)
                Log.d("Res","Outside" + t.message)

            }
        })

        return plantIdentificationDataList
    }





}