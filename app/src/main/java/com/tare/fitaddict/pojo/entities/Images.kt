package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("LARGE")
    val lARGE: LARGE = LARGE(),
    @SerializedName("REGULAR")
    val rEGULAR: REGULAR = REGULAR(),
    @SerializedName("SMALL")
    val sMALL: SMALL = SMALL(),
    @SerializedName("THUMBNAIL")
    val tHUMBNAIL: THUMBNAIL = THUMBNAIL()
)