package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.search.FoodSearchViewModel

data class Hint(
    @SerializedName("food")
    val food: Food = Food(),
    @SerializedName("measures")
    val measures: List<Measure> = listOf()
){
    fun onClick()
    {
        FoodSearchViewModel().addFood(this)
    }
}