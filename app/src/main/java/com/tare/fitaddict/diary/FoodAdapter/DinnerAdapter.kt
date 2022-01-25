package com.tare.fitaddict.diary.FoodAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.ItemFoodBinding
import com.tare.fitaddict.pojo.entities.Food
import com.tare.fitaddict.utils.DiffUtilFood

class DinnerAdapter : RecyclerView.Adapter<DinnerAdapter.VH>() {
    private var itemList: List<Food> = listOf()

    inner class VH(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Food) {
            binding.item = currentItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v: ItemFoodBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_food,
            parent,
            false
        )
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun addAll(updated: List<Food>?) {
        val old = itemList
        itemList = updated ?: listOf()
        val diff = DiffUtil.calculateDiff(DiffUtilFood(old, itemList), true)
        diff.dispatchUpdatesTo(this)
    }
}