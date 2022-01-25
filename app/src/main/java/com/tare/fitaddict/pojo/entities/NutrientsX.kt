package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class NutrientsX(
    @SerializedName("CHOCDF")
    val cHOCDF: Double = 0.0,
    @SerializedName("ENERC_KCAL")
    val eNERCKCAL: Double = 0.0,
    @SerializedName("FAT")
    val fAT: Double = 0.0,
    @SerializedName("FIBTG")
    val fIBTG: Double = 0.0,
    @SerializedName("PROCNT")
    val pROCNT: Double = 0.0
)