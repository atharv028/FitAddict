package com.tare.fitaddict.login

data class User(
    val email : String,
    val name : String,
    val age : Int? = 0,
    val sex : String? = null,
    val goal : String? = null,
    val activity : String? = null,
    val height : String? = null,
    val weight : String? = null,
    val calorieRequirements : Int? = null
)