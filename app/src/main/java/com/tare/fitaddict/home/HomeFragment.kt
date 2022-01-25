package com.tare.fitaddict.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.FragmentHome2Binding

class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHome2Binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home2, container, false
        )
        binding.apply {
            lifecycleOwner = this@HomeFragment
            viewModel = homeViewModel
        }
        return binding.root
    }
}