package com.tare.fitaddict.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.FragmentWorkoutBinding

class WorkoutFragment : Fragment() {
    private val workoutViewModel by viewModels<WorkoutViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentWorkoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false)
        binding.apply {
            lifecycleOwner = this@WorkoutFragment
            viewModel = workoutViewModel
        }
        workoutViewModel.selected.observe(viewLifecycleOwner) {
            it?.let {
                binding.viewModel?.fetchWorkout(it)
            }
        }
        return binding.root
    }
}