package com.eunidev.edcopid_19.model_data

import com.google.gson.annotations.SerializedName

class CovidModel {

    data class CovidIndonesia(@SerializedName("name") val name: String,
                              @SerializedName("positif") val positive: String,
                              @SerializedName("sembuh") val getWell: String,
                              @SerializedName("meninggal") val death: String)

    data class CovidIndonesiaProvinsi(@SerializedName("FID") val fid: Int,
                                      @SerializedName("Kode_Provi") val provinceCode: Int,
                                      @SerializedName("Provinsi") val province: String,
                                      @SerializedName("Kasus_Posi") val positive: Int,
                                      @SerializedName("Kasus_Semb") val getWell: Int,
                                      @SerializedName("Kasus_Meni") val death: Int)

    data class ParentCIP(@SerializedName("attributes") val attributes: CovidIndonesiaProvinsi)
}