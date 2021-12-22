package com.tare.fitaddict.home

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.MySingleton
import com.tare.fitaddict.R
import com.tare.fitaddict.nutrition.Nutrition
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment(), Clicked {
    private lateinit var recyclerView : RecyclerView
    private lateinit var mAdapter : adapter
    private lateinit var llHome : LinearLayout
    private lateinit var calGoal : TextView
    private lateinit var calFood : TextView
    private lateinit var calRemaining : TextView
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var date : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_home2, container, false)
        recyclerView = view.findViewById(R.id.homeRV)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        date = current.format(formatter)
        calGoal = view.findViewById(R.id.Calgoal)
        calFood = view.findViewById(R.id.calFood)
        calRemaining = view.findViewById(R.id.calRemaining)
        mAdapter = adapter(this)
        llHome = view.findViewById(R.id.llTop)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
        fillInfoAboutCalories()
        return view
    }

    private fun fetchData()
    {
        Log.d("TAG", "Called")
        val url2 = "https://newsapi.org/v2/everything?q=diet&apiKey=1120ca2de3f6495f83caef9b575d68ea&pageSize=100"
        val jsonObjectRequest : JsonObjectRequest = object : JsonObjectRequest(Method.GET, url2, null,
            {
                val newsArray = it.getJSONArray("articles")
                val listNews = ArrayList<news>()
                for(i in 0 until newsArray.length())
                {
                    Log.d("TAG", "Updating")
                    val jsonObject = newsArray.getJSONObject(i)
                    val data = news(jsonObject.getString("title"),jsonObject.getString("author"),
                    jsonObject.getString("description"),jsonObject.getString("url"),
                    jsonObject.getString("urlToImage"),jsonObject.getString("publishedAt"))
                    listNews.add(data)
                }
                listNews.shuffle()
                mAdapter.update(listNews)
            },
            {
                it.printStackTrace()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
        MySingleton.getInstance(activity?.applicationContext!!).addToRequestQueue(jsonObjectRequest)
    }

    override fun onclick(item: news) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(activity, Uri.parse(item.url))
    }

    private fun fillInfoAboutCalories()
    {
        db.collection("users").document(auth.currentUser?.email!!)
            .get()
            .addOnSuccessListener { doc ->
                if(doc != null)
                {
                    calGoal.text = doc.data?.get("calorieRequirements").toString()
                }
                else
                {
                    Log.d("TAG", "Document does not exist")
                }
            }
            .addOnFailureListener {
                Log.w("TAG", "Task failed with:", it)
            }
        db.collection(date).document(auth.currentUser?.email!!)
            .get()
            .addOnSuccessListener { doc->
                if(doc != null)
                {
                    calFood.text = doc.getLong("consumedCalories").toString()
                    calRemaining.text = doc.getLong("remainingCalories").toString()
                }
            }
    }
}