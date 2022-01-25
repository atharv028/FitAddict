package com.tare.fitaddict.pojo.response


import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.pojo.entities.MealItem

data class ResponseMealItem(
    @SerializedName("meals")
    val meals: List<MealItem> = listOf()
)