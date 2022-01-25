package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Parsed(
    @SerializedName("food")
    val food: Food = Food()
)