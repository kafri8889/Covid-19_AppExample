package com.eunidev.edcopid_19.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIInit {

    fun createApi(): APIClient {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.kawalcorona.com/")
            .build()
            .create(APIClient::class.java)
    }
}