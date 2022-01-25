package com.tare.fitaddict.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tare.fitaddict.R
import com.tare.fitaddict.databinding.ResultItemBinding
import com.tare.fitaddict.pojo.entities.Hint
import com.tare.fitaddict.utils.DiffUtilHint

@BindingAdapter(value = ["searchItemViewModel","searchItemView"], requireAll = true)
fun bindSearch(recyclerView: RecyclerView, itemList: List<Hint>?, viewModel: FoodSearchViewModel)
{
    val adapter = checkAdapterSearch(recyclerView)
    adapter.viewModel = viewModel
    adapter.update(itemList)
}


private fun checkAdapterSearch(recyclerView: RecyclerView): ResultAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is ResultAdapter) {
        recyclerView.adapter as ResultAdapter
    } else {
        val homeNewsAdapter = ResultAdapter()
        recyclerView.adapter = homeNewsAdapter
        homeNewsAdapter
    }
}

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.VH>() {
    private var items : List<Hint>  = listOf()
    lateinit var viewModel: FoodSearchViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: ResultItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.result_item,parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun update(updated : List<Hint>?)
    {
        val old = items
        items = updated ?: listOf()
        val diff = DiffUtil.calculateDiff(DiffUtilHint(old,items),true)
        diff.dispatchUpdatesTo(this)
        Log.d("TAG", "Updated")
    }

    inner class VH(private val binding: ResultItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(currentItem: Hint)
        {
            binding.item= currentItem
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

}
