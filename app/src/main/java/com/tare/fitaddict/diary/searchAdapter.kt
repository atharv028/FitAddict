package com.tare.fitaddict.diary

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import android.widget.Filterable
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.RequestFuture
import com.tare.fitaddict.MySingleton
import org.json.JSONArray
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

class searchAdapter(context: Context) : ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line,android.R.id.text1), Filterable {
    var suggestions : ArrayList<String> = ArrayList()
    override fun getCount(): Int {
        return suggestions.size
    }

    override fun getItem(position: Int): String? {
        return suggestions[position]
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if(constraint != null)
                {
                    suggestions = autocomplete(constraint.toString())
                    //fetch(constraint.toString(), this@searchAdapter.context).execute()
                    filterResults.values = suggestions
                    filterResults.count = suggestions.size
                }
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if(results != null && results.count > 0)
                {
                    notifyDataSetChanged()
                }
                else
                    notifyDataSetInvalidated()
            }
        }
        return filter
    }
    private fun autocomplete(query : String) : ArrayList<String>
    {
        var result : ArrayList<String> = ArrayList()
        val req : RequestFuture<JSONArray> = RequestFuture.newFuture()

        val url = "https://api.edamam.com/auto-complete?app_id=ea1ff3d4&app_key=6860bf7450da64d0f5a6861e4b074cca&q=${query}"
        val json = JsonArrayRequest(url, req, req)
        MySingleton(this.context).addToRequestQueue(json)
        try {
            val arr = req.get(30, TimeUnit.SECONDS)
            for(i in 0 until arr.length())
            {
                Log.d("TAG", "added ${arr[i].toString()}")
                result.add(arr[i].toString())
            }
        }catch (e : InterruptedException)
        {

        }catch (e : ExecutionException){

        }

//        val jsonArrayReq = JsonArrayRequest(
//            Request.Method.GET, url, null,
//            {arr ->
//                for(i in 0 until arr.length())
//                {
//                    Log.d("TAG", "added ${arr[i].toString()}")
//                    result.add(arr[i].toString())
//                }
//            },
//            {
//                it.printStackTrace()
//            })
//        MySingleton.getInstance(this.context).addToRequestQueue(jsonArrayReq)
//        Log.d("TAG", result.size.toString())
//        for (i in result)
//            Log.d("TAG", "list item $i")
        return result
    }
}

