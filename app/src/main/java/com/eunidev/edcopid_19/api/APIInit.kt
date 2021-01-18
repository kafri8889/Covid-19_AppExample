package com.eunidev.edcopid_19.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIInit {

    fun createApi(): APIClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val OHClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder()
            .client(OHClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.kawalcorona.com/")
            .build()
            .create(APIClient::class.java)
    }
}