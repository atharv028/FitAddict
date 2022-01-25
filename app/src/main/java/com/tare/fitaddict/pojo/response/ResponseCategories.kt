package com.tare.fitaddict.pojo.response


import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.pojo.entities.Category

data class ResponseCategories(
    @SerializedName("categories")
    val categories: List<Category> = listOf()
)