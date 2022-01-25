package com.tare.fitaddict.diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tare.fitaddict.firebase.AuthHelper
import com.tare.fitaddict.home.HomeRepository
import com.tare.fitaddict.home.HomeUseCase
import com.tare.fitaddict.pojo.entities.Food
import com.tare.fitaddict.search.FoodSearch
import com.tare.fitaddict.utils.DateHelper
import kotlinx.coroutines.launch
import kotlin.math.floor

class DiaryViewModel : ViewModel() {
    private val currentUser = AuthHelper.currentUser()
    private var date = DateHelper.setDate()
    var goalCalories: LiveData<String> = HomeRepository.goalCalories
    var remainingCalories: LiveData<String> = HomeUseCase.remainingCalories
    var consumedCalories: LiveData<String> = HomeUseCase.consumedCalories
    var breakfastList: LiveData<List<Food>> = DiaryRepository.breakfastList
    var lunchList: LiveData<List<Food>> = DiaryRepository.lunchList
    var dinnerList: LiveData<List<Food>> = DiaryRepository.dinnerList
    var snackList: LiveData<List<Food>> = DiaryRepository.snackList
    var shownDate = MutableLiveData<String>()
    var breakfastCalories = MutableLiveData<String>()
    var lunchCalories = MutableLiveData<String>()
    var dinnerCalories = MutableLiveData<String>()
    var snackCalories = MutableLiveData<String>()

    init {
        updateBreakfast()
        updateDinner()
        updateLunch()
        updateSnack()
        fillDate()
        setItems()
    }

    fun updateBreakfast() {
        breakfastList.value?.let {
            var ans = 0.0
            for (i in it)
                ans += i.nutrients.eNERCKCAL
            breakfastCalories.postValue(floor(ans).toInt().toString())
        }
    }

    fun updateLunch() {
        lunchList.value?.let {
            var ans = 0.0
            for (i in it)
                ans += i.nutrients.eNERCKCAL
            lunchCalories.postValue(floor(ans).toInt().toString())
        }
    }

    fun updateDinner() {
        dinnerList.value?.let {
            var ans = 0.0
            for (i in it)
                ans += i.nutrients.eNERCKCAL
            dinnerCalories.postValue(floor(ans).toInt().toString())
        }
    }

    fun updateSnack() {
        snackList.value?.let {
            var ans = 0.0
            for (i in it)
                ans += i.nutrients.eNERCKCAL
            snackCalories.postValue(floor(ans).toInt().toString())
        }
    }

    private fun setItems() {
        viewModelScope.launch {
            currentUser?.email?.let {
                DiaryRepository.fillBreakfast(it, date)
                DiaryRepository.fillLunch(it, date)
                DiaryRepository.fillDinner(it, date)
                DiaryRepository.fillSnacks(it, date)
            }
        }
    }

    fun addFood(view: View, type: String) {
        val intent = Intent(view.context, FoodSearch::class.java)
        val bundle = Bundle()
        bundle.putString("type", type)
        intent.putExtras(bundle)
        startActivity(view.context, intent, bundle)
    }

    fun dateMinus() {
        date = (date.toInt() - 1).toString()
        fillDate()
        setItems()
    }

    fun datePlus() {
        date = (date.toInt() + 1).toString()
        fillDate()
        setItems()
    }

    private fun fillDate() {
        val day = date.substring(date.lastIndex - 1)
        val month = date.substring(date.lastIndex - 3, date.lastIndex - 1)
        val year = date.substring(0, 4)
        Log.d("TAG", "$day $month $year")
        shownDate.postValue("$day-$month-$year")
    }
}