package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class VITB12(
    @SerializedName("label")
    val label: String = "",
    @SerializedName("quantity")
    val quantity: Double = 0.0,
    @SerializedName("unit")
    val unit: String = ""
)