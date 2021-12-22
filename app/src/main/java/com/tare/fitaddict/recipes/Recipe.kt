package com.tare.fitaddict.recipes

data class Recipe(
    val uri : String,
    val label : String,
    val imgUrl : String,
    val calories : String,
    val indigrients: ArrayList<String>,
    val values : ArrayList<String>,
    val serving : String,
)
