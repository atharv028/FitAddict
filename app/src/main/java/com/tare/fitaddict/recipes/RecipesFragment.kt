package com.tare.fitaddict.recipes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tare.fitaddict.MainActivity
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.FragmentRecipeBinding
import com.tare.fitaddict.recipes.viewrecipe.ViewRecipeFramgent


class RecipesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper: Context = ContextThemeWrapper(activity, R.style.Theme_FitAddict)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val viewm = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
        val binding: FragmentRecipeBinding = DataBindingUtil.inflate(localInflater,R.layout.fragment_recipe,container,false)
        // inflate the layout using the cloned inflater, not default inflater
        binding.apply{
            lifecycleOwner = this@RecipesFragment
            viewModel = viewm
        }
        viewm.mealDetails.observe(viewLifecycleOwner){
            if(it != null)
            {
                val framgent = ViewRecipeFramgent()
                (activity as MainActivity).openSubContentFragment(framgent)
            }
        }
        viewm.youtubeOpen.observe(viewLifecycleOwner){
            if(it != null)
            {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(webIntent)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        activity?.actionBar?.show()
    }
}