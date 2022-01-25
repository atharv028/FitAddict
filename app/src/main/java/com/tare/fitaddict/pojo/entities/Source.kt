package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: Any? = null,
    @SerializedName("name")
    val name: String = ""
)