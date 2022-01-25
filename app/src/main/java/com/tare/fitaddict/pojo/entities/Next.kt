package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Next(
    @SerializedName("href")
    val href: String = "",
    @SerializedName("title")
    val title: String = ""
)