package com.tare.fitaddict.diary.FoodAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tare.fitaddict.R
import com.tare.fitaddict.diary.ResObj

class SnackAdapter : RecyclerView.Adapter<SnackAdapter.VH>() {
    private val arrayList : ArrayList<ResObj> = ArrayList()
    inner class VH(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val name : TextView = itemView.findViewById(R.id.TVDiaryFoodName)
        val calorie : TextView = itemView.findViewById(R.id.TVDiaryCal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_diary,parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val curr = arrayList[position]
        holder.name.text = curr.label
        holder.calorie.text = "${curr.nutrients[0].toString()} cal"
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun addAll(list : ArrayList<ResObj>)
    {
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }
    fun print()
    {
        Log.d("TAG", arrayList.toString())
    }
}