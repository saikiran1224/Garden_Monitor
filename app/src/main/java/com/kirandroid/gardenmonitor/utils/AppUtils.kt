package com.kirandroid.gardenmonitor.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {
    private var instance: AppUtils? = null
    const val plantNetApiKey:String = "2b106yy0yLNcCZELH9t9nzUFbO"

    const val plantNetUrl = "https://my-api.plantnet.org/v2/"
    const val wikiUrl = "https://en.wikipedia.org/api/rest_v1/"


    fun AppUtils() {
        instance = this
    }

    fun getInstance(): AppUtils? {
        return instance
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String? {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String? {
        val sdf = SimpleDateFormat("HH:mm")
        val currentDate = sdf.format(Date())
        return "$currentDate IST"
    }





}