package com.eunidev.edcopid_19.api

import com.eunidev.edcopid_19.model_data.CovidModel
import retrofit2.Call
import retrofit2.http.GET

interface APIClient {

    @GET("/")
    fun getGlobalData(): Call<List<CovidModel.ParentCG>>

    @GET("/indonesia")
    fun getIndonesiaData(): Call<List<CovidModel.CovidIndonesia>>

    @GET("/indonesia/provinsi")
    fun getProvinceData(): Call<List<CovidModel.ParentCIP>>
}