package com.kirandroid.gardenmonitor.retrofit

import com.kirandroid.gardenmonitor.BuildConfig
/*
import jdk.nashorn.internal.runtime.Source.baseURL
*/
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitUtils {

    val TAG: String = RetrofitUtils::class.java.getName()
    private const val CONNECTION_TIMEOUT = 60
    private val isDebugging: Boolean = BuildConfig.DEBUG
    private var retrofit: Retrofit? = null

   /* fun getInstance(): Retrofit? {
        if (retrofit == null)

            retrofit = Retrofit.Builder()
            .baseUrl("https://my-api.plantnet.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()
        return retrofit!!
    }

    fun getWikiInstance(): Retrofit? {
        if (retrofit == null)

            retrofit = Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/api/rest_v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build()
        return retrofit!!
    }*/

    fun getInstance(baseURL: String):Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build()
        } else {
            if (!retrofit!!.baseUrl().equals(baseURL)) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient())
                    .build()
            }
        }
        return retrofit
    }


    private fun getHttpClient(): OkHttpClient? {
        return getHttpClient(null)
    }

    private fun getHttpClient(headerInterceptor: Interceptor?): OkHttpClient? {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (isDebugging) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        httpClient.addInterceptor(interceptor)
        if (headerInterceptor != null) {
            httpClient.addInterceptor(headerInterceptor)
        }
        return httpClient.build()
    }




}
