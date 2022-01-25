package com.tare.fitaddict.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tare.fitaddict.pojo.entities.UserCalories
import com.tare.fitaddict.firebase.FirestoreHelper
import com.tare.fitaddict.network.RestClient
import com.tare.fitaddict.pojo.response.ResponseNews
import com.tare.fitaddict.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object HomeRepository {
    private val _responseNews = MutableLiveData<ResponseNews>()
    private val _goalCalories = MutableLiveData<String>()
    private val _userCalories = MutableLiveData<UserCalories>()
    val goalCalories: LiveData<String>
        get() = _goalCalories
    val userCalories: LiveData<UserCalories>
        get() = _userCalories
    val responseNews: LiveData<ResponseNews>
        get() = _responseNews

    fun fetchNews() {
        val webService = RestClient.create()
        webService.getArticles(Constants.NEWSURL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _responseNews.postValue(it)
            }
    }

    suspend fun setGoalCalories(email: String) {
        val dbService = FirestoreHelper.getGoalCalories(email)
        _goalCalories.postValue(dbService)
    }

    suspend fun setRemainingCalories(date: String, email: String) {
        val dbService = FirestoreHelper.getCalories(date, email)
        dbService?.let {
            _userCalories.postValue(it)
        }
    }
}