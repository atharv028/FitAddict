package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Measure(
    @SerializedName("label")
    val label: String = "",
    @SerializedName("qualified")
    val qualified: List<Qualified> = listOf(),
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("weight")
    val weight: Double = 0.0
)