package com.tare.fitaddict.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.ItemNewsBinding
import com.tare.fitaddict.pojo.entities.Article
import com.tare.fitaddict.utils.DiffUtilNews

class HomeNewsAdapter : RecyclerView.Adapter<BindableViewHolder>() {
    private var itemList: List<Article> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val v: ItemNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_news,
            parent,
            false
        )
        return BindableViewHolder(v)
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun update(updated: List<Article>?) {
        val oldList = itemList
        itemList = updated ?: emptyList()
        val diffResult = DiffUtil.calculateDiff(DiffUtilNews(oldList, itemList), true)
        diffResult.dispatchUpdatesTo(this)
    }
}

class BindableViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(currentItem: Article) {
        binding.item = currentItem
        binding.executePendingBindings()
    }
}
