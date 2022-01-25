package com.tare.fitaddict.recipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tare.fitaddict.network.RestClient
import com.tare.fitaddict.pojo.response.ResponseCategories
import com.tare.fitaddict.pojo.response.ResponseMeal
import com.tare.fitaddict.pojo.response.ResponseMealItem
import com.tare.fitaddict.pojo.response.ResponseRecipe
import com.tare.fitaddict.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object RecipeRepository {
    private val _responseCategories = MutableLiveData<ResponseCategories>()
    val responseCategories: LiveData<ResponseCategories>
        get() = _responseCategories

    private val _responseMeal = MutableLiveData<ResponseMeal>()
    val responseMeal: LiveData<ResponseMeal>
        get() = _responseMeal

    private val _responseMealDetails = MutableLiveData<ResponseMealItem>()
    val responseMealDetails: LiveData<ResponseMealItem>
        get() = _responseMealDetails

    fun getCategories() {
        val webService = RestClient.create()
        webService.getCategories(Constants.CATEGORIESURL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _responseCategories.postValue(it)
            }, {
                Log.e("RecipeRepository", "Error: ", it)
            })
    }

    fun getMeal(query: String) {
        val webService = RestClient.create()
        webService.getMeal("https://www.themealdb.com/api/json/v1/1/filter.php?c=$query")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _responseMeal.postValue(it)
            }, {
                Log.e("RecipeRepository", "Error: ", it)
            })
    }

    fun getMealDetails(mealId: String) {
        val webService = RestClient.create()
        webService.getMealDetails("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _responseMealDetails.postValue(it)
            }, {
                Log.e("RecipeRepository", "Error :", it)
            })
    }
}