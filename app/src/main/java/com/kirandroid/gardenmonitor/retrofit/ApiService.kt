package com.kirandroid.gardenmonitor.retrofit

import com.google.gson.JsonObject
import com.kirandroid.gardenmonitor.responses.PlantIdentificationResponse
import com.kirandroid.gardenmonitor.responses.WikiPlantProfileResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @GET("identify/all")
    fun PlantIdentificationResponse(@Query("images") images:ArrayList<String>, @Query("organs") organs:ArrayList<String>, @Query("include-related-images") boolean: Boolean, @Query("lang") lang:String, @Query("api-key") apikey: String): Call<PlantIdentificationResponse?>?

/*
    https://my-api.plantnet.org/v2/identify/all?
    images=https%3A%2F%2Fi.postimg.cc%2FP5q9CHjg%2FWhats-App-Image-2021-06-16-at-1-24-02-PM-4.jpg
    &images=https%3A%2F%2Fi.postimg.cc%2FvmrrM1Kb%2FWhats-App-Image-2021-06-16-at-1-24-02-PM-3.jpg
    &images=https%3A%2F%2Fi.postimg.cc%2F76TyfkgH%2FWhats-App-Image-2021-06-16-at-1-24-01-PM.jpg
    &organs=leaf&organs=flower&organs=fruit
    &include-related-images=true
    &lang=en
    &api-key=2b106yy0yLNcCZELH9t9nzUFbO
*/

}