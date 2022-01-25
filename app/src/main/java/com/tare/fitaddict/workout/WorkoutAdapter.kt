package com.tare.fitaddict.workout

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.ItemWorkoutBinding
import com.tare.fitaddict.pojo.entities.ResponseWorkoutItem
import com.tare.fitaddict.utils.DiffUtilWorkout

class WorkoutAdapter: RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {
    private var workoutList: List<ResponseWorkoutItem> = listOf()
    inner class ViewHolder(private val binding : ItemWorkoutBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: ResponseWorkoutItem)
        {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: ItemWorkoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_workout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(workoutList[position])
    }

    override fun getItemCount(): Int = workoutList.size

    fun update(updated: List<ResponseWorkoutItem>?)
    {
        Log.d("WOKROUT", "CALLED WITH $updated")
        val old = workoutList
        workoutList = updated ?: emptyList()
        val diff = DiffUtil.calculateDiff(DiffUtilWorkout(old,workoutList),true)
        diff.dispatchUpdatesTo(this)
    }

}