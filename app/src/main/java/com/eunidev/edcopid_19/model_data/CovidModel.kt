package com.eunidev.edcopid_19.model_data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CovidModel {

    data class CovidIndonesia(@SerializedName("name") val name: String,
                              @SerializedName("dirawat") val dirawat: String,
                              @SerializedName("positif") val positive: String,
                              @SerializedName("sembuh") val getWell: String,
                              @SerializedName("meninggal") val death: String) : Serializable

    data class CovidGlobal(@SerializedName("OBJECTID") val objectId: Int,
                           @SerializedName("Country_Region") val countryRegion: String,
                           @SerializedName("Last_Update") val lastUpdateInMillis: Long,
                           @SerializedName("Lat") val latidude: Double,
                           @SerializedName("Long_") val longitude: Double,
                           @SerializedName("Confirmed") val confirmed: Int,
                           @SerializedName("Recovered") val recovered: Int,
                           @SerializedName("Deaths") val deaths: Int,
                           @SerializedName("Active") val active: Int) : Serializable

    data class CovidIndonesiaProvinsi(@SerializedName("FID") val fid: Int,
                                      @SerializedName("Kode_Provi") val provinceCode: Int,
                                      @SerializedName("Provinsi") val province: String,
                                      @SerializedName("Kasus_Posi") val positive: Int,
                                      @SerializedName("Kasus_Semb") val getWell: Int,
                                      @SerializedName("Kasus_Meni") val death: Int) : Serializable

    data class ParentCIP(@SerializedName("attributes") val attributes: CovidIndonesiaProvinsi) : Serializable
    data class ParentCG(@SerializedName("attributes") val attributes: CovidGlobal) : Serializable
}