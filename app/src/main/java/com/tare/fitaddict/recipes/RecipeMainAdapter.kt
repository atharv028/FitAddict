package com.tare.fitaddict.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.ItemRecipeMainBinding
import com.tare.fitaddict.pojo.entities.Category
import com.tare.fitaddict.utils.DiffUtilCategory

@BindingAdapter("categoriesList", "categoryViewModel", requireAll = true)
fun bindCategory(
    recyclerView: RecyclerView,
    itemList: List<Category>?,
    viewModel: RecipeViewModel
) {
    val adapter = checkOrCreateAdapter(recyclerView)
    adapter.update(itemList)
    adapter.recipeViewModel = viewModel
}

private fun checkOrCreateAdapter(recyclerView: RecyclerView): RecipeMainAdapter {
    return if (recyclerView.adapter is RecipeMainAdapter && recyclerView.adapter != null) {
        recyclerView.adapter as RecipeMainAdapter
    } else {
        val adapter = RecipeMainAdapter()
        recyclerView.adapter = adapter
        adapter
    }
}

class RecipeMainAdapter : RecyclerView.Adapter<RecipeMainAdapter.RecipeMainViewHolder>() {
    private var itemList: List<Category> = listOf()
    lateinit var recipeViewModel: RecipeViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeMainViewHolder {
        val binding: ItemRecipeMainBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recipe_main, parent, false
        )
        return RecipeMainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeMainViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun update(updated: List<Category>?) {
        val old = itemList
        itemList = updated ?: listOf()
        val diff = DiffUtil.calculateDiff(DiffUtilCategory(old, itemList), true)
        diff.dispatchUpdatesTo(this)
    }

    inner class RecipeMainViewHolder(private val binding: ItemRecipeMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Category) {
            binding.item = currentItem
            binding.viewModel = recipeViewModel
            binding.executePendingBindings()
        }
    }
}

