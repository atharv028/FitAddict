package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("next")
    val next: Next = Next()
)