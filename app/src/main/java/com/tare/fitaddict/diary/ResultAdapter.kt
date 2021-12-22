package com.tare.fitaddict.diary

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tare.fitaddict.R

class ResultAdapter(private val click : ClickHandler) : RecyclerView.Adapter<ResultAdapter.VH>() {
    private  var clickHandler: ClickHandler = click
    private val items : ArrayList<ResObj>  = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_item,parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val curr = items[position]
        holder.clickHandler = this.clickHandler
        holder.category.text = curr.category
        holder.label.text = curr.label
        holder.calories.text = "${curr.nutrients[0]} kcal"
        if(curr.img != "NA")
            Glide.with(holder.itemView.context).load(curr.img).into(holder.img)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(updated : ArrayList<ResObj>)
    {

        items.clear()
        items.addAll(updated)
        notifyDataSetChanged()
        Log.d("TAG", "Updated")
    }

    inner class VH(item : View) : RecyclerView.ViewHolder(item), View.OnClickListener
    {
        lateinit var clickHandler: ClickHandler
        val img: ImageView = item.findViewById(R.id.IMGres)
        val label: TextView = item.findViewById(R.id.TVRes)
        val category: TextView = item.findViewById(R.id.TVcategory)
        val calories : TextView = item.findViewById(R.id.TVCaloriesRes)
        val button : Button = item.findViewById(R.id.buttonFoodAdd)
        init {
            button.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if(clickHandler != null)
            {
                clickHandler.onAddButtonClicked(items[adapterPosition])
            }
        }
    }
    interface ClickHandler{
        fun onAddButtonClicked(item: ResObj)
    }

}
