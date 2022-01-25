package com.tare.fitaddict.pojo.response


import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.pojo.entities.Article

data class ResponseNews(
    @SerializedName("articles")
    val articles: List<Article> = listOf(),
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0
)