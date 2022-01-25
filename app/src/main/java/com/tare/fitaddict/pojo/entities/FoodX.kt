package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class FoodX(
    @SerializedName("category")
    val category: String = "",
    @SerializedName("categoryLabel")
    val categoryLabel: String = "",
    @SerializedName("foodId")
    val foodId: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("label")
    val label: String = "",
    @SerializedName("nutrients")
    val nutrients: Nutrients = Nutrients()
)