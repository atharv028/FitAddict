package com.tare.fitaddict.pojo.response


import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.pojo.entities.Hint
import com.tare.fitaddict.pojo.entities.Links
import com.tare.fitaddict.pojo.entities.Parsed

data class ResponseFoodSearch(
    @SerializedName("hints")
    val hints: List<Hint> = listOf(),
    @SerializedName("_links")
    val links: Links = Links(),
    @SerializedName("parsed")
    val parsed: List<Parsed> = listOf(),
    @SerializedName("text")
    val text: String = ""
)