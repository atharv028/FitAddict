package com.tare.fitaddict.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.ItemRecipeSubBinding
import com.tare.fitaddict.pojo.entities.Meal
import com.tare.fitaddict.utils.DiffUtilMeal

@BindingAdapter("mealList", "mealViewModel", requireAll = true)
fun bindMeal(recyclerView: RecyclerView, itemList: List<Meal>?, viewModel: RecipeViewModel) {
    val adapter = checkOrCreate(recyclerView)
    adapter.recipeViewModel = viewModel
    adapter.update(itemList)
}

private fun checkOrCreate(recyclerView: RecyclerView): RecipeSubAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is RecipeSubAdapter) {
        recyclerView.adapter as RecipeSubAdapter
    } else {
        val adapter = RecipeSubAdapter()
        recyclerView.adapter = adapter
        adapter
    }
}

class RecipeSubAdapter : RecyclerView.Adapter<RecipeSubAdapter.RecipeSubViewHolder>() {
    private var itemList: List<Meal> = listOf()
    lateinit var recipeViewModel: RecipeViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeSubViewHolder {
        val binding: ItemRecipeSubBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recipe_sub, parent, false
        )
        return RecipeSubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeSubViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun update(updated: List<Meal>?) {
        val old = itemList
        itemList = updated ?: listOf()
        val diff = DiffUtil.calculateDiff(DiffUtilMeal(old, itemList), true)
        diff.dispatchUpdatesTo(this)
    }

    inner class RecipeSubViewHolder(private val binding: ItemRecipeSubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Meal) {
            binding.item = currentItem
            binding.viewModel = recipeViewModel
            binding.executePendingBindings()
        }
    }
}