package com.tare.fitaddict.diary

import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.diary.FoodAdapter.BreakfastAdapter
import com.tare.fitaddict.diary.FoodAdapter.DinnerAdapter
import com.tare.fitaddict.diary.FoodAdapter.LunchAdapter
import com.tare.fitaddict.diary.FoodAdapter.SnackAdapter

class FoodFiller {
    private var auth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()
    fun fillBreakfast(adapter : BreakfastAdapter, id : String, date : String, calories : TextView)
    {
        val ref = db.collection(date).document(id)
            .collection("breakfast")

        ref.get()
            .addOnSuccessListener { res ->
                val items : ArrayList<ResObj> = ArrayList()
                var totalCal = 0
                for (doc in res)
                {
                    Log.d("TAG", "${doc.id} => ${doc.data}")
                    val img = doc.get("img") as String
                    val label = doc.getString("label")!!
                    val category = doc.get("category") as String
                    val nutri = doc.get("nutrients") as ArrayList<*>
                    val nutrient : ArrayList<Int> = ArrayList()
                    nutri.forEach{
                        nutrient.add((it as Long).toInt())
                    }
                    val obj = ResObj(label, category, img, nutrient)
                    totalCal += nutrient[0]
                    items.add(obj)
                }
                Log.d("TAG", "$items , $totalCal" )
                calories.text = "${totalCal} cal"
                adapter.addAll(items)
            }
            .addOnFailureListener {
                Log.w("TAG", "Failed to retrieve breakfast collection because:", it)
            }

    }
    fun fillLunch(adapter : LunchAdapter, id : String, date : String, calories: TextView)
    {
        val ref = db.collection(date).document(id)
            .collection("lunch")

        ref.get()
            .addOnSuccessListener { res ->
                val items : ArrayList<ResObj> = ArrayList()
                var totalCal = 0
                for (doc in res)
                {
                    Log.d("TAG", "${doc.id} => ${doc.data}")
                    val img = doc.get("img") as String
                    val label = doc.getString("label")!!
                    val category = doc.get("category") as String
                    val nutri = doc.get("nutrients") as ArrayList<*>
                    val nutrient : ArrayList<Int> = ArrayList()
                    nutri.forEach{
                        nutrient.add((it as Long).toInt())
                    }
                    val obj = ResObj(label, category, img, nutrient)
                    totalCal += nutrient[0]
                    items.add(obj)
                }
                Log.d("TAG", items.toString())
                calories.text = "${totalCal} cal"
                adapter.addAll(items)
            }
            .addOnFailureListener {
                Log.w("TAG", "Failed to retrieve breakfast collection because:", it)
            }

    }
    fun fillDinner(adapter : DinnerAdapter, id : String, date : String, calories: TextView)
    {
        val ref = db.collection(date).document(id)
            .collection("dinner")

        ref.get()
            .addOnSuccessListener { res ->
                val items : ArrayList<ResObj> = ArrayList()
                var totalCal = 0
                for (doc in res)
                {
                    Log.d("TAG", "${doc.id} => ${doc.data}")
                    val img = doc.get("img") as String
                    val label = doc.getString("label")!!
                    val category = doc.get("category") as String
                    val nutri = doc.get("nutrients") as ArrayList<*>
                    val nutrient : ArrayList<Int> = ArrayList()
                    nutri.forEach{
                        nutrient.add((it as Long).toInt())
                    }
                    val obj = ResObj(label, category, img, nutrient)
                    totalCal += nutrient[0]
                    items.add(obj)
                }
                Log.d("TAG", items.toString())
                calories.text = "${totalCal} cal"
                adapter.addAll(items)
            }
            .addOnFailureListener {
                Log.w("TAG", "Failed to retrieve breakfast collection because:", it)
            }

    }
    fun fillSnack(adapter : SnackAdapter, id : String, date : String, calories: TextView)
    {
        val ref = db.collection(date).document(id)
            .collection("snack")

        ref.get()
            .addOnSuccessListener { res ->
                val items : ArrayList<ResObj> = ArrayList()
                var totalCal = 0
                for (doc in res)
                {
                    Log.d("TAG", "${doc.id} => ${doc.data}")
                    val img = doc.get("img") as String
                    val label = doc.getString("label")!!
                    val category = doc.get("category") as String
                    val nutri = doc.get("nutrients") as ArrayList<*>
                    val nutrient : ArrayList<Int> = ArrayList()
                    nutri.forEach{
                        nutrient.add((it as Long).toInt())
                    }
                    val obj = ResObj(label, category, img, nutrient)
                    totalCal += nutrient[0]
                    items.add(obj)
                }
                Log.d("TAG", items.toString())
                calories.text = "${totalCal} cal"
                adapter.addAll(items)
            }
            .addOnFailureListener {
                Log.w("TAG", "Failed to retrieve breakfast collection because:", it)
            }

    }
}