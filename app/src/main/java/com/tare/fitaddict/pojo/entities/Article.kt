package com.tare.fitaddict.pojo.entities


import android.net.Uri
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import com.google.gson.annotations.SerializedName
import com.tare.fitaddict.home.HomeViewModel

data class Article(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("publishedAt")
    val publishedAt: String = "",
    @SerializedName("source")
    val source: Source = Source(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val urlToImage: String = ""
){
    fun onClick(view: View)
    {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(view.context, Uri.parse(url))
    }
}