package com.tare.fitaddict.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tare.fitaddict.pojo.entities.Category
import com.tare.fitaddict.pojo.entities.Meal
import com.tare.fitaddict.pojo.entities.MealItem

class RecipeViewModel : ViewModel() {
    var categories: LiveData<List<Category>> = RecipeUseCase.categoryList
    var mealList: LiveData<List<Meal>> = RecipeUseCase.mealList
    var mealDetails: LiveData<MealItem> = RecipeUseCase.mealDetails
    var clickedCategory = MutableLiveData("Please select a")
    var youtubeOpen = MutableLiveData<String>()
    var ingredients: LiveData<String> = RecipeUseCase.ingredients
    var backPressed = MutableLiveData<Boolean>(false)
    init {
        fetchCategories()
        RecipeRepository.getMeal("Vegan")
    }

    private fun fetchCategories() {
        RecipeRepository.getCategories()
    }

    fun onItemClick(item: Meal) {
        RecipeRepository.getMealDetails(item.idMeal)
    }

    fun onCategoryClick(item: Category) {
        clickedCategory.value = item.strCategory
        RecipeRepository.getMeal(item.strCategory)
    }

    fun onYoutubeClick(url: String) {
        youtubeOpen.value = url
    }

    fun onBackClick()
    {
        backPressed.value = true
    }

}