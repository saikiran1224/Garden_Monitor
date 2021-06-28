package com.kirandroid.gardenmonitor.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {
    private var instance: AppUtils? = null

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