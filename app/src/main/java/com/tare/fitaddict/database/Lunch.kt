package com.tare.fitaddict.database

data class FoodItem(
    val type : String,
    val name : String,
    val calories : Int,
    val protein : Int,
    val carbs : Int,
    val fat : Int,
)