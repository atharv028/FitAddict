package com.tare.fitaddict.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tare.fitaddict.R
import java.util.*
import kotlin.collections.ArrayList

class Adapter(private val listener : Clicked) : RecyclerView.Adapter<ViewHolder>() {
    private val exercise : ArrayList<exer> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            listener.onClick(exercise[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curr = exercise[position]
        holder.title.text = curr.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        holder.target.text = curr.target
        var url = curr.gifUrl
        val temp = url.substring(4)
        url = "https$temp"
        Glide.with(holder.itemView.context).load(url).into(holder.gif)
    }

    override fun getItemCount(): Int {
        return exercise.size
    }

    fun update(updated : ArrayList<exer>)
    {
        exercise.clear()
        exercise.addAll(updated)
        notifyDataSetChanged()
    }

}
class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val title = itemView.findViewById<TextView>(R.id.titleTV)
    val target = itemView.findViewById<TextView>(R.id.targetTV)
    val gif = itemView.findViewById<ImageView>(R.id.gifIV)
}

interface Clicked
{
    fun onClick(item : exer)
}

