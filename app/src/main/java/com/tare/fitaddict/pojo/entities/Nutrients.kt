package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Nutrients(
    @SerializedName("CHOCDF")
    var cHOCDF: Double = 0.0,
    @SerializedName("ENERC_KCAL")
    var eNERCKCAL: Double = 0.0,
    @SerializedName("FAT")
    var fAT: Double = 0.0,
    @SerializedName("FIBTG")
    var fIBTG: Double = 0.0,
    @SerializedName("PROCNT")
    var pROCNT: Double = 0.0
)