package com.tare.fitaddict.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tare.fitaddict.pojo.entities.Category
import com.tare.fitaddict.pojo.entities.Meal
import com.tare.fitaddict.pojo.entities.MealItem
import com.tare.fitaddict.pojo.response.ResponseCategories
import com.tare.fitaddict.pojo.response.ResponseMeal
import com.tare.fitaddict.pojo.response.ResponseMealItem

object RecipeUseCase {
    private val rawCategories: LiveData<ResponseCategories> = RecipeRepository.responseCategories
    val categoryList: LiveData<List<Category>> = category()

    private val rawMeal: LiveData<ResponseMeal> = RecipeRepository.responseMeal
    val mealList: LiveData<List<Meal>> = meal()

    private val rawMealDetails: LiveData<ResponseMealItem> = RecipeRepository.responseMealDetails
    val mealDetails: LiveData<MealItem> = mealDetails()

    val ingredients: LiveData<String> = ingredients()

    private fun category(): LiveData<List<Category>> {
        val repo = rawCategories
        val ans = Transformations.map(repo) {
            newCategory(it)
        }
        return ans
    }

    private fun newCategory(item: ResponseCategories): List<Category> {
        return item.categories
    }

    private fun meal(): LiveData<List<Meal>> {
        val repo = rawMeal
        val ans = Transformations.map(repo) {
            newMeal(it)
        }
        return ans
    }

    private fun newMeal(item: ResponseMeal): List<Meal> {
        return item.meals
    }

    private fun mealDetails(): LiveData<MealItem> {
        val repo = rawMealDetails
        val ans = Transformations.map(repo) {
            mealDet(it)
        }
        return ans
    }

    private fun mealDet(item: ResponseMealItem): MealItem {
        return item.meals[0]
    }

    private fun ingredients(): LiveData<String> {
        val repo = rawMealDetails
        val ans = Transformations.map(repo) {
            mealIngredients(it)
        }
        return ans
    }

    private fun mealIngredients(it: ResponseMealItem): String {
        var ans = ""
        val item = it.meals[0]
        if (!item.strIngredient1.isNullOrEmpty())
            ans += "${item.strIngredient1}: ${item.strMeasure1} \n"
        if (!item.strIngredient2.isNullOrEmpty())
            ans += "${item.strIngredient2}: ${item.strMeasure2} \n"
        if (!item.strIngredient3.isNullOrEmpty())
            ans += "${item.strIngredient3}: ${item.strMeasure3} \n"
        if (!item.strIngredient4.isNullOrEmpty())
            ans += "${item.strIngredient4}: ${item.strMeasure4} \n"
        if (!item.strIngredient5.isNullOrEmpty())
            ans += "${item.strIngredient5}: ${item.strMeasure5} \n"
        if (!item.strIngredient6.isNullOrEmpty())
            ans += "${item.strIngredient6}: ${item.strMeasure6} \n"
        if (!item.strIngredient7.isNullOrEmpty())
            ans += "${item.strIngredient7}: ${item.strMeasure7} \n"
        if (!item.strIngredient8.isNullOrEmpty())
            ans += "${item.strIngredient8}: ${item.strMeasure8} \n"
        if (!item.strIngredient9.isNullOrEmpty())
            ans += "${item.strIngredient9}: ${item.strMeasure9} \n"
        if (!item.strIngredient10.isNullOrEmpty())
            ans += "${item.strIngredient10}: ${item.strMeasure10} \n"
        if (!item.strIngredient11.isNullOrEmpty())
            ans += "${item.strIngredient11}: ${item.strMeasure11} \n"
        if (!item.strIngredient12.isNullOrEmpty())
            ans += "${item.strIngredient12}: ${item.strMeasure12} \n"
        if (!item.strIngredient13.isNullOrEmpty())
            ans += "${item.strIngredient13}: ${item.strMeasure13} \n"
        if (!item.strIngredient14.isNullOrEmpty())
            ans += "${item.strIngredient14}: ${item.strMeasure14} \n"
        if (!item.strIngredient15.isNullOrEmpty())
            ans += "${item.strIngredient15}: ${item.strMeasure15} \n"
        if (!item.strIngredient16.isNullOrEmpty())
            ans += "${item.strIngredient16}: ${item.strMeasure16} \n"
        if (!item.strIngredient17.isNullOrEmpty())
            ans += "${item.strIngredient17}: ${item.strMeasure17} \n"
        if (!item.strIngredient18.isNullOrEmpty())
            ans += "${item.strIngredient18}: ${item.strMeasure18} \n"
        if (!item.strIngredient19.isNullOrEmpty())
            ans += "${item.strIngredient19}: ${item.strMeasure19} \n"
        if (!item.strIngredient20.isNullOrEmpty())
            ans += "${item.strIngredient20}: ${item.strMeasure20} \n"
        return ans
    }
}