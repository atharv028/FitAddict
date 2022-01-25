package com.tare.fitaddict.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tare.fitaddict.pojo.entities.Food
import com.tare.fitaddict.pojo.entities.Hint
import com.tare.fitaddict.pojo.response.ResponseFoodSearch

object SearchUseCase {
    private var rawData: LiveData<ResponseFoodSearch> = SearchRepository.searchResults
    val itemList: LiveData<List<Hint>> = convert()

    private fun convert(): LiveData<List<Hint>>
    {
        val repo = rawData
        val ans = Transformations.map(repo){
            createList(it)
        }
        return ans
    }

    private fun createList(response: ResponseFoodSearch): List<Hint>
    {
        return response.hints
    }

    suspend fun addFood(item: Hint, date: String, email: String, type: String)
    {
        val ans = item.food
        SearchRepository.addFood(ans,date,email,type)
    }
}