package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("calories")
    val calories: Double = 0.0,
    @SerializedName("cautions")
    val cautions: List<String> = listOf(),
    @SerializedName("cuisineType")
    val cuisineType: List<String> = listOf(),
    @SerializedName("dietLabels")
    val dietLabels: List<String> = listOf(),
    @SerializedName("digest")
    val digest: List<Digest> = listOf(),
    @SerializedName("dishType")
    val dishType: List<String> = listOf(),
    @SerializedName("healthLabels")
    val healthLabels: List<String> = listOf(),
    @SerializedName("image")
    val image: String = "",
    @SerializedName("images")
    val images: Images = Images(),
    @SerializedName("ingredientLines")
    val ingredientLines: List<String> = listOf(),
    @SerializedName("ingredients")
    val ingredients: List<Ingredient> = listOf(),
    @SerializedName("label")
    val label: String = "",
    @SerializedName("mealType")
    val mealType: List<String> = listOf(),
    @SerializedName("shareAs")
    val shareAs: String = "",
    @SerializedName("source")
    val source: String = "",
    @SerializedName("totalDaily")
    val totalDaily: TotalDaily = TotalDaily(),
    @SerializedName("totalNutrients")
    val totalNutrients: TotalNutrients = TotalNutrients(),
    @SerializedName("totalTime")
    val totalTime: Double = 0.0,
    @SerializedName("totalWeight")
    val totalWeight: Double = 0.0,
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("yield")
    val yield: Double = 0.0
)