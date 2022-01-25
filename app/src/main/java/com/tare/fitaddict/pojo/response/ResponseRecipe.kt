package com.tare.fitaddict.pojo.response


import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.pojo.entities.Hit
import com.tare.fitaddict.pojo.entities.Links
import com.tare.fitaddict.pojo.entities.LinksX

data class ResponseRecipe(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("from")
    val from: Int = 0,
    @SerializedName("hits")
    val hits: List<Hit> = listOf(),
    @SerializedName("_links")
    val links: Links = Links(),
    @SerializedName("to")
    val to: Int = 0
)