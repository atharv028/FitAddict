package com.tare.fitaddict.diary

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.MainActivity
import com.tare.fitaddict.MySingleton
import com.tare.fitaddict.R
import com.tare.fitaddict.diary.FoodAdapter.BreakfastAdapter
import com.tare.fitaddict.diary.FoodAdapter.DinnerAdapter
import com.tare.fitaddict.diary.FoodAdapter.LunchAdapter
import com.tare.fitaddict.diary.FoodAdapter.SnackAdapter
import org.json.JSONException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

class FoodSearch : AppCompatActivity() {
    private lateinit var autoComplete : AutoCompleteTextView
    private lateinit var button : ImageButton
    private lateinit var recyclerView : RecyclerView
    private lateinit var mAdapter : ResultAdapter
    private lateinit var type : String
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_search)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val date = current.format(formatter)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        autoComplete = findViewById(R.id.AutoFood)
        button = findViewById(R.id.IBsearch)
        recyclerView = findViewById(R.id.RVFoodSearch)
        type = intent.getStringExtra("type")!!
        autoComplete.threshold = 2
        mAdapter = ResultAdapter(object : ResultAdapter.ClickHandler{
            override fun onAddButtonClicked(item : ResObj) {
                Toast.makeText(this@FoodSearch, "${item.label} added successfully in the ${type.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }} list", Toast.LENGTH_SHORT).show()
                db.collection(date).document(auth.currentUser?.email!!)
                    .collection(type).add(item)
                    .addOnSuccessListener {
                        Log.d("TAG", "${item.label} for type $type added into database")
                    }
                    .addOnFailureListener {
                        Log.w("TAG", "couldn't add item ${item.label} in db because of ", it)
                    }
                val ref = db.collection(date).document(auth.currentUser?.email!!)

                db.runTransaction {
                    val snap = it.get(ref)
                    val newCal = snap.getLong("consumedCalories")?.plus(item.nutrients[0])
                    val newRemain = snap.get("remainingCalories").toString().toInt() - item.nutrients[0]
                    it.update(ref,"consumedCalories",newCal)
                    it.update(ref,"remainingCalories",newRemain)
                }
                    .addOnSuccessListener {
                        Log.d("TAG", "Updated Calories")
                    }
                    .addOnFailureListener {
                        Log.d("TAG", "Failed to update Calories")
                    }

            }

        })
        button.setOnClickListener {
            if(autoComplete.editableText.isEmpty())
            {
                Toast.makeText(this, "Please enter something in search field", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            fetchData(autoComplete.editableText.toString())
        }
        recyclerView.adapter = mAdapter
        autoComplete.setAdapter(searchAdapter(this))

    }

    private fun fetchData(query : String)
    {
        val url = "https://api.edamam.com/api/food-database/v2/parser?app_id=ea1ff3d4&app_key=6860bf7450da64d0f5a6861e4b074cca&ingr=$query&nutrition-type=cooking"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val hits = it.getJSONArray("hints")
                val ans : ArrayList<ResObj> = ArrayList()
                for(i in 0 until hits.length())
                {
                    val curr = hits.getJSONObject(i)
                    val food = curr.getJSONObject("food")
                    val label = food.getString("label")
                    var calorie = food.getJSONObject("nutrients").getString("ENERC_KCAL")
                    calorie = floor(calorie.toDouble()).toInt().toString()
                    var protein : String
                    try {
                        protein = food.getJSONObject("nutrients").getString("PROCNT")
                        protein = floor(protein.toDouble()).toInt().toString()
                    }catch (e : JSONException)
                    {
                        protein = "0"
                    }
                    var carbs : String
                    try {
                        carbs = food.getJSONObject("nutrients").getString("CHOCDF")
                        carbs = floor(carbs.toDouble()).toInt().toString()
                    }catch (e : JSONException)
                    {
                        carbs = "0"
                    }
                    var fat : String
                    try {
                        fat = food.getJSONObject("nutrients").getString("FAT")
                        fat = floor(fat.toDouble()).toInt().toString()
                    }catch (e : JSONException)
                    {
                        fat = "0"
                    }
                    val category = food.getString("category")
                    var img: String
                    try {
                        img = food.getString("image")
                    }catch (e : JSONException)
                    {
                        img = "NA"
                    }

                    ans.add(ResObj(label,category,img, arrayListOf(calorie.toInt(),protein.toInt(),carbs.toInt(),fat.toInt())))
                }
                mAdapter.update(ans)
            }
        ,{})
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)

    }
}