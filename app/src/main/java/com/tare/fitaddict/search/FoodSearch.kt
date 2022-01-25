package com.tare.fitaddict.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tare.fitaddict.MainActivity
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.ActivityFoodSearchBinding

class FoodSearch : AppCompatActivity() {
    private val searchViewModel by viewModels<FoodSearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityFoodSearchBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_food_search)
        binding.apply {
            lifecycleOwner = this@FoodSearch
            viewModel = searchViewModel
        }
        val type = intent.extras?.getString("type")!!
        searchViewModel.selectedItem.observe(this) {
            searchViewModel.addFoodToDB(type)
            searchViewModel.calculate()
            searchViewModel.updateUserCalories()
        }
        attachObserver()
    }

    fun attachObserver() {
        searchViewModel.isAdded.observe(this) {
            if (it) {
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }
}