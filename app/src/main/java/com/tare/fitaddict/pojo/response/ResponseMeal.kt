package com.tare.fitaddict.pojo.response


import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.pojo.entities.Meal

data class ResponseMeal(
    @SerializedName("meals")
    val meals: List<Meal> = listOf()
)