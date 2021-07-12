package com.kirandroid.gardenmonitor.retrofit

import com.google.gson.JsonObject
import com.kirandroid.gardenmonitor.responses.PlantIdentificationResponse
import com.kirandroid.gardenmonitor.responses.WikiPlantProfileResponse
import retrofit2.Call
import retrofit2.http.*

interface WikiService {

    // Wikimedia API
    @GET("page/summary/{title}")
    fun WikiPlantProfileResponse(@Path("title") commonName: String): Call<WikiPlantProfileResponse?>?

}