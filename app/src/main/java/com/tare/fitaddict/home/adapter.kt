package com.tare.fitaddict.home

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tare.fitaddict.R

class adapter(private val listener : Clicked) : RecyclerView.Adapter<ViewHolder>() {
    private val items : ArrayList<news> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.temp, parent, false)
        val viewholder = ViewHolder(view)
        view.setOnClickListener {
            listener.onclick(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curr = items[position]
        holder.title.text = curr.title
        var date : String = ""
        for(i in 0..9)
        {
            date += curr.timeAdded[i]
        }
        if(curr.author == null)
        {
            holder.authorTime.text = "Published on ${date}"
        }else
            holder.authorTime.text = "Published by ${curr.author} on ${date}"

        if(curr.description.isEmpty())
        {
            holder.description.text = "No Description Available!"
        }
        else
            holder.description.text = curr.description

        Glide.with(holder.itemView.context).load(curr.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(updated : ArrayList<news>)
    {
        items.clear()
        items.addAll(updated)

        notifyDataSetChanged()
    }
}

class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    val title = ItemView.findViewById<TextView>(R.id.titleTextViewID)
    val authorTime = ItemView.findViewById<TextView>(R.id.published)
    val description = ItemView.findViewById<TextView>(R.id.detailsTextViewID)
    val image = ItemView.findViewById<ImageView>(R.id.imgID)
}

interface Clicked{
    fun onclick(item : news)
}