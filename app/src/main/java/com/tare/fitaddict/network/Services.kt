package com.tare.fitaddict.network

import com.tare.fitaddict.pojo.response.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface Services {

    @GET("/api/recipes/v2?type=public&app_id=ac674c93&app_key=aea845be4afa2b71bcb4c4afb45ce87d")
    fun searchRecipe(@Query("q") q: String): Observable<ResponseRecipe>

    @GET("/api/food-database/v2/parser?app_id=ea1ff3d4&app_key=6860bf7450da64d0f5a6861e4b074cca&nutrition-type=cooking")
    fun getFood(@Query("ingr") ingr: String): Observable<ResponseFoodSearch>

    @GET("/auto-complete?app_id=ea1ff3d4&app_key=6860bf7450da64d0f5a6861e4b074cca")
    fun autoCompleteSearch(@Query("q") q: String): Observable<ResponseAutoComplete>

    @GET
    fun getCategories(@Url url: String): Observable<ResponseCategories>

    @GET
    fun getMeal(@Url url: String): Observable<ResponseMeal>

    @GET
    fun getMealDetails(@Url url: String): Observable<ResponseMealItem>

    @GET
    fun getArticles(@Url url: String): Observable<ResponseNews>

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: e4dae4e0a2msh9a8df3b86af5de1p1dcc95jsna5f9853ab7e0"
    )
    @GET
    fun getWorkout(@Url url: String): Observable<ResponseWorkout>

}