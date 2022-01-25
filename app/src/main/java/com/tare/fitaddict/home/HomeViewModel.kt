package com.tare.fitaddict.home

import androidx.lifecycle.*
import com.tare.fitaddict.firebase.AuthHelper
import com.tare.fitaddict.pojo.entities.Article
import com.tare.fitaddict.utils.DateHelper
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    companion object {
        private const val TAG = "HOME VIEW MODEL"
    }
    private val currentUser = AuthHelper.currentUser()
    private var date = DateHelper.setDate()
    var goalCalories: LiveData<String> = HomeRepository.goalCalories
    var consumedCalories: LiveData<String> = HomeUseCase.consumedCalories
    var remainingCalories: LiveData<String> = HomeUseCase.remainingCalories
    var newsLiveData: LiveData<List<Article>> = HomeUseCase.newsArticles

    init {
        setCalories()
    }

    private fun setCalories() {
        viewModelScope.launch {
            currentUser?.email?.let {
                HomeRepository.setGoalCalories(it)
                HomeRepository.setRemainingCalories(date, it)
            }
        }

    }
}