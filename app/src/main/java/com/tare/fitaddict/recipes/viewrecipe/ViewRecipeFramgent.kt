package com.tare.fitaddict.recipes.viewrecipe

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tare.fitaddict.R
import com.tare.fitaddict.recipes.RecipeViewModel
import com.tare.fitaddict.databinding.FragmentRecipeViewBinding


class ViewRecipeFramgent : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recipeViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
        val binding: FragmentRecipeViewBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_recipe_view,container,false)
        binding.apply{
            lifecycleOwner = activity
            viewModel = recipeViewModel
            item = recipeViewModel.mealDetails.value
        }

        recipeViewModel.backPressed.observe(viewLifecycleOwner){
            if(it != false)
            {
                activity?.supportFragmentManager?.popBackStackImmediate()
                recipeViewModel.backPressed.value = false
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP)
                {
                    activity?.supportFragmentManager?.popBackStackImmediate()
                    return true
                }
                return false
            }
        })
    }
}