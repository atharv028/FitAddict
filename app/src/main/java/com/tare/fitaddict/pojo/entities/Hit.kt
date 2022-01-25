package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Hit(
    @SerializedName("_links")
    val links: Links = Links(),
    @SerializedName("recipe")
    val recipe: Recipe = Recipe()
)