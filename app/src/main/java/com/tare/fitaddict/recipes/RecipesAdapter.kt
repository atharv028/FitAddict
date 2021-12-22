package com.tare.fitaddict.recipes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.appevents.suggestedevents.ViewOnClickListener
import com.tare.fitaddict.R
import kotlin.math.floor

class RecipesAdapter(private val listener : Listen) : RecyclerView.Adapter<ViewHolder>() {
    private val list : ArrayList<Recipe> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item,parent, false)
        val viewholder = ViewHolder(view)
        view.setOnClickListener {
            Log.d("TAG", viewholder.adapterPosition.toString())
            listener.onClickedRecipe(list[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curr = list[position]
        val temp = floor(curr.calories.toDouble()).toInt()
        holder.calorie.text = "$temp cal"
        holder.title.text = curr.label

        Glide.with(holder.itemView.context).load(curr.imgUrl).into(holder.img)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(updated : ArrayList<Recipe>)
    {
        list.clear()
        updated.shuffle()
        list.addAll(updated)

        notifyDataSetChanged()
    }

}

class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val img: ImageView = itemView.findViewById(R.id.IVcalImg)
    val title: TextView = itemView.findViewById(R.id.TVcalTitle)
    val calorie: TextView = itemView.findViewById(R.id.TVcalFood)
}

interface Listen
{
    fun onClickedRecipe(item : Recipe)
}