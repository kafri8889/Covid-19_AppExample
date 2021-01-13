package com.eunidev.edcopid_19.api

import com.eunidev.edcopid_19.model_data.CovidModel
import retrofit2.Call
import retrofit2.http.GET

interface APIClient {

    @GET("/indonesia")
    fun getIndonesiaData(): Call<List<CovidModel.CovidIndonesia>>

    @GET("/indonesia/provinsi")
    fun getProvinceData(): Call<List<CovidModel.CovidIndonesiaProvinsi>>

    @GET("/indonesia/provinsi")
    fun getProvinceData2(): Call<List<CovidModel.ParentCIP>>
}