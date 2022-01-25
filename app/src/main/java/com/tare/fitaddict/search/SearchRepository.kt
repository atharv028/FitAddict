package com.tare.fitaddict.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tare.fitaddict.firebase.FirestoreHelper
import com.tare.fitaddict.network.RestClient
import com.tare.fitaddict.pojo.entities.Food
import com.tare.fitaddict.pojo.entities.Hint
import com.tare.fitaddict.pojo.response.ResponseFoodSearch
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object SearchRepository {
    private var _searchResults = MutableLiveData<ResponseFoodSearch>()
    val searchResults: LiveData<ResponseFoodSearch>
        get() = _searchResults
    private var _isAdded = MutableLiveData(false)
    val isAdded: LiveData<Boolean>
        get() = _isAdded

    fun fetchSearchResults(query: String) {
        val webService = RestClient.create()
        webService.getFood(query)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchResults.postValue(it)
            }, {})
    }

    suspend fun addFood(item: Food,date: String, email:String, type:String)
    {
        val dbService = FirestoreHelper.addFood(item,date, email, type)
        _isAdded.postValue(dbService)
    }
}