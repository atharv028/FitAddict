package com.tare.fitaddict.recipes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tare.fitaddict.MySingleton
import com.tare.fitaddict.R
import java.util.*
import kotlin.collections.ArrayList


class RecipesFragment : Fragment(), Listen {
    private lateinit var recyclerView1: RecyclerView
    private lateinit var query : TextInputEditText
    private lateinit var layout : TextInputLayout
    private lateinit var head : TextView
    private var mAdapter = RecipesAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper: Context = ContextThemeWrapper(activity, R.style.Theme_FitAddict)

        // clone the inflater using the ContextThemeWrapper

        // clone the inflater using the ContextThemeWrapper
        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        // inflate the layout using the cloned inflater, not default inflater

        // inflate the layout using the cloned inflater, not default inflater
        val v = localInflater.inflate(R.layout.temp2, container, false)
        // val v =  inflater.inflate(R.layout.temp2, container, false)
        recyclerView1 = v.findViewById(R.id.recycler_first)
        query = v.findViewById(R.id.ETrecipe)
        layout = v.findViewById(R.id.TILlayout)
        head = v.findViewById(R.id.TVrecipe)
        recyclerView1.apply {
            fetchData("chicken")
            adapter = mAdapter
        }
        layout.setEndIconOnClickListener {
            if(query.editableText.isEmpty())
            {
                layout.isErrorEnabled = true
                return@setEndIconOnClickListener
            }
            fetchData(query.editableText.toString())
            head.text = query.editableText.toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }

        return v
    }

    private fun fetchData(item : String)
    {
        Log.d("TAG", "Called")
        val url = "https://api.edamam.com/api/recipes/v2?type=public&app_id=ac674c93&app_key=aea845be4afa2b71bcb4c4afb45ce87d&q=$item"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url ,null,
            {
                Log.d("TAG", "Recipe Update")
                val hits = it.getJSONArray("hits")
                val list : ArrayList<Recipe> = ArrayList()
                for(i in 0 until hits.length())
                {
                    val recipe = hits.getJSONObject(i).getJSONObject("recipe")
                    val uri = recipe.getString("uri")
                    val label = recipe.getString("label")
                    val imageUrl = recipe.getString("image")
                    val indigrients : ArrayList<String> = ArrayList()
                    val arr = recipe.getJSONArray("ingredientLines")
                    for(j in 0 until arr.length())
                    {
                        indigrients.add(arr[j].toString())
                    }
                    val calories = recipe.getString("calories")
                    val digest = recipe.getJSONArray("digest")
                    val value : ArrayList<String> = ArrayList()
//                    for(j in 0 until digest.length())
//                    {
//                        val curr = digest.getJSONObject(i)
//                        var fat  = ""
//                        var protein  = ""
//                        var carbs  = ""
//                        if(curr.getString("label").toString() == "Fat")
//                        {
//                            fat = curr.getString("total")
//                        }
//                        else if(curr.getString("label").toString() == "Carbs")
//                        {
//                            carbs = curr.getString("total")
//                        }
//                        else if(curr.getString("label").toString() == "Protein")
//                        {
//                            protein = curr.getString("total")
//                        }
//                        else
//                            continue
//
//                        values.add(Values(fat,carbs, protein))
//                    }
                    for(j in 0..2)
                    {
                        val curr = digest.getJSONObject(j)
                        value.add(curr.getString("total"))
                    }
                    val serving = recipe.getString("yield")
                    list.add(Recipe(uri,label,imageUrl,calories,indigrients,value,serving))
                }
                mAdapter.update(list)
            },
            {})
        MySingleton.getInstance(activity?.applicationContext!!).addToRequestQueue(jsonObjectRequest)
    }

    override fun onClickedRecipe(item: Recipe) {
        Log.d("TAG", "Clicked on recipe")
        val intent = Intent(this.context, RecipeView::class.java)
        val list = item.values
        val indi = item.indigrients
        intent.putStringArrayListExtra("nutrition",list)
        intent.putStringArrayListExtra("ingredients", indi)
        intent.putExtra("imgUrl", item.imgUrl)
        intent.putExtra("name", item.label)
        intent.putExtra("serving", item.serving)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        activity?.actionBar?.show()
    }
}