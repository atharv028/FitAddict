package com.tare.fitaddict.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tare.fitaddict.pojo.entities.ResponseWorkoutItem
import com.tare.fitaddict.pojo.response.ResponseWorkout

object WorkoutUseCase {
    private val rawData = WorkoutRepository.responseWorkout
    val responseWorkout: LiveData<List<ResponseWorkoutItem>> = convert()

    fun convert() : LiveData<List<ResponseWorkoutItem>>
    {
        val repo = rawData
        val ans = Transformations.map(repo){
            createWork(it)
        }
        return ans
    }
    private fun createWork(item: ResponseWorkout): List<ResponseWorkoutItem>
    {
        return item.subList(0,item.size-1)
    }
}