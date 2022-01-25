package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class LARGE(
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("width")
    val width: Int = 0
)