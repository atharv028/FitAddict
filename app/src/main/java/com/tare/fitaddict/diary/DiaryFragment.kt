package com.tare.fitaddict.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {
    private val diaryViewModel by viewModels<DiaryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDiaryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_diary, container, false)
        binding.apply {
            lifecycleOwner = this@DiaryFragment
            viewModel = diaryViewModel
        }
        attachObserver()
        return binding.root
    }

    private fun attachObserver()
    {
        diaryViewModel.breakfastList.observe(viewLifecycleOwner){
            diaryViewModel.updateBreakfast()
        }
        diaryViewModel.lunchList.observe(viewLifecycleOwner){
            diaryViewModel.updateLunch()
        }
        diaryViewModel.dinnerList.observe(viewLifecycleOwner){
            diaryViewModel.updateDinner()
        }
        diaryViewModel.snackList.observe(viewLifecycleOwner){
            diaryViewModel.updateSnack()
        }
    }
}