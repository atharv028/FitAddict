package com.tare.fitaddict.pojo.entities


import android.util.Log
import com.google.gson.annotations.SerializedName

data class Food(
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
    var nutrients: Nutrients = Nutrients()
){
    fun onClick()
    {
        Log.d("CLICKED", "CLICKED")
    }
}