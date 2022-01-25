package com.tare.fitaddict.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.FragmentFormBinding
import kotlin.math.log

class FormFragment : Fragment() {
    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFormBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_form, container, false)
        binding.apply {
            viewModel = loginViewModel
            lifecycleOwner = this@FormFragment
        }
        attachObservers()
        return binding.root
    }

    private fun attachObservers()
    {
        loginViewModel.isComplete.observe(viewLifecycleOwner){

        }
    }

}