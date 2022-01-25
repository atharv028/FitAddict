package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("food")
    val food: String = "",
    @SerializedName("foodCategory")
    val foodCategory: String? = null,
    @SerializedName("foodId")
    val foodId: String = "",
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("measure")
    val measure: String? = null,
    @SerializedName("quantity")
    val quantity: Double = 0.0,
    @SerializedName("text")
    val text: String = "",
    @SerializedName("weight")
    val weight: Double = 0.0
)