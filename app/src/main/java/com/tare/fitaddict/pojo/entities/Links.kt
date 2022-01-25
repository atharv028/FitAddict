package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Links(
    @SerializedName("next")
    val next: Next = Next()
)