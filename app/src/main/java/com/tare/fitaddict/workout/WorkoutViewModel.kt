package com.tare.fitaddict.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tare.fitaddict.pojo.entities.ResponseWorkoutItem
import java.util.*

class WorkoutViewModel: ViewModel() {
    val spinnerEntries = WorkoutRepository.workoutEntries
    var selected = MutableLiveData<String>()
    var responseWorkout = WorkoutUseCase.responseWorkout
    var isSelected: Boolean = ResponseWorkoutItem().isClicked

    fun fetchWorkout(bodyPart: String)
    {
        WorkoutRepository.fetchWorkout(bodyPart.lowercase(Locale.getDefault()))
    }


}