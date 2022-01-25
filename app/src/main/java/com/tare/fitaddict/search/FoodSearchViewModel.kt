package com.tare.fitaddict.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tare.fitaddict.firebase.AuthHelper
import com.tare.fitaddict.firebase.FirestoreHelper
import com.tare.fitaddict.home.HomeRepository
import com.tare.fitaddict.home.HomeUseCase
import com.tare.fitaddict.pojo.entities.Hint
import com.tare.fitaddict.pojo.entities.UserCalories
import com.tare.fitaddict.utils.DateHelper
import kotlinx.coroutines.launch

class FoodSearchViewModel : ViewModel() {
    private val date = DateHelper.setDate()
    private val currentUser = AuthHelper.currentUser()
    var searchResult: LiveData<List<Hint>> = SearchUseCase.itemList
    var query = MutableLiveData<String>()
    val type = MutableLiveData("")
    var consumedCalories = HomeUseCase.consumedCalories
    var remainingCalories = HomeUseCase.remainingCalories
    var isAdded: LiveData<Boolean> = SearchRepository.isAdded
    val userCalories: MutableLiveData<UserCalories?> by lazy {
        MutableLiveData()
    }
    val selectedItem: MutableLiveData<Hint> by lazy {
        MutableLiveData()
    }

    fun fetchSearchResult() {
        query.value?.let {
            if (it.length > 4) {
                SearchRepository.fetchSearchResults(it)
            }
        }
    }

    fun addFood(item: Hint) {
        selectedItem.value = item
    }

    fun addFoodToDB(type: String) {
        viewModelScope.launch {
            currentUser?.email?.let { email ->
                selectedItem.value?.let {
                    SearchUseCase.addFood(it, date, email, type)
                }
            }
        }
    }

    fun updateUserCalories() {
        Log.d("CALLED ", "CALLED WITH ${userCalories.value}")
        viewModelScope.launch {
            currentUser?.email?.let { email ->
                userCalories.value?.let {
                    FirestoreHelper.updateCalories(it, date, email)
                }
            }
        }
    }

    fun calculate()
    {
        val newCal = selectedItem.value?.food?.nutrients?.eNERCKCAL?.let {
            consumedCalories.value?.toDouble()?.plus(
                it
            )
        }?.toInt()
        val newRemain = selectedItem.value?.food?.nutrients?.eNERCKCAL?.let {
            remainingCalories.value?.toDouble()?.minus(
                it
            )
        }?.toInt()
        if(newCal != null && newRemain != null)
            userCalories.value = UserCalories(newCal,newRemain)

    }
}