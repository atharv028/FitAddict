package com.tare.fitaddict.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tare.fitaddict.firebase.FirestoreHelper
import com.tare.fitaddict.pojo.entities.Food

object DiaryRepository {
    private val _breakfastList = MutableLiveData<List<Food>>()
    private val _lunchList = MutableLiveData<List<Food>>()
    private val _dinnerList = MutableLiveData<List<Food>>()
    private val _snackList = MutableLiveData<List<Food>>()

    val breakfastList: LiveData<List<Food>>
        get() = _breakfastList
    val lunchList: LiveData<List<Food>>
        get() = _lunchList
    val dinnerList: LiveData<List<Food>>
        get() = _dinnerList
    val snackList: LiveData<List<Food>>
        get() = _snackList

    suspend fun fillBreakfast(email: String, data: String) {
        val dbService = FirestoreHelper.getFoodItemByType(data, email, "breakfast")
        _breakfastList.postValue(dbService)
    }

    suspend fun fillLunch(email: String, data: String) {
        val dbService = FirestoreHelper.getFoodItemByType(data, email, "lunch")
        _lunchList.postValue(dbService)
    }

    suspend fun fillDinner(email: String, data: String) {
        val dbService = FirestoreHelper.getFoodItemByType(data, email, "dinner")
        _dinnerList.postValue(dbService)
    }

    suspend fun fillSnacks(email: String, data: String) {
        val dbService = FirestoreHelper.getFoodItemByType(data, email, "snack")
        _snackList.postValue(dbService)
    }
}