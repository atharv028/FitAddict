package com.tare.fitaddict.login

data class User(
    var email : String,
    var name : String,
    var age : Int? = 0,
    var sex : String? = null,
    var goal : String? = null,
    var activity : String? = null,
    var height : String? = null,
    var weight : String? = null,
    var calorieRequirements : Int? = null
)