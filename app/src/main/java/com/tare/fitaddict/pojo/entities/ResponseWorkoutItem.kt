package com.tare.fitaddict.pojo.entities


import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.R

data class ResponseWorkoutItem(
    @SerializedName("bodyPart")
    val bodyPart: String = "",
    @SerializedName("equipment")
    val equipment: String = "",
    @SerializedName("gifUrl")
    val gifUrl: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("target")
    val target: String = ""
){
    var isClicked: Boolean = false
    fun onClick(view: View)
    {
        isClicked = !isClicked
    }
}