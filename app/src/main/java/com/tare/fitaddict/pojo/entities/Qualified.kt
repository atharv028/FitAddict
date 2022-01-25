package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Qualified(
    @SerializedName("qualifiers")
    val qualifiers: List<Qualifier> = listOf(),
    @SerializedName("weight")
    val weight: Double = 0.0
)