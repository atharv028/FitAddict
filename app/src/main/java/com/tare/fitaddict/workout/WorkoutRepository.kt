package com.tare.fitaddict.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tare.fitaddict.network.RestClient
import com.tare.fitaddict.pojo.response.ResponseWorkout
import com.tare.fitaddict.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object WorkoutRepository {
    private val _responseWorkout = MutableLiveData<ResponseWorkout>()
    val responseWorkout: LiveData<ResponseWorkout>
        get() = _responseWorkout
    val workoutEntries = listOf("Chest", "Back", "Shoulders", "Cardio", "Arms", "Legs", "Neck", "Waist")

    fun fetchWorkout(bodyPart: String)
    {
        val webService = RestClient.create()
        webService.getWorkout(Constants.WORKOUTURL.plus(bodyPart))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                _responseWorkout.postValue(it)
            }
    }
}