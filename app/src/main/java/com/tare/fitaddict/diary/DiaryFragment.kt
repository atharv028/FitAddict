package com.tare.fitaddict.diary

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.R
import com.tare.fitaddict.diary.FoodAdapter.BreakfastAdapter
import com.tare.fitaddict.diary.FoodAdapter.DinnerAdapter
import com.tare.fitaddict.diary.FoodAdapter.LunchAdapter
import com.tare.fitaddict.diary.FoodAdapter.SnackAdapter
import com.tare.fitaddict.home.news
import com.tare.fitaddict.nutrition.Nutrition
import java.nio.charset.IllegalCharsetNameException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DiaryFragment : Fragment() {
    private lateinit var breakfast : TextView
    private lateinit var lunch : TextView
    private lateinit var dinner : TextView
    private lateinit var snack : TextView
    private lateinit var calGoal : TextView
    private lateinit var calFood : TextView
    private lateinit var calRemaining : TextView
    private lateinit var linearLayout : LinearLayout
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var breakfastRV : RecyclerView
    private lateinit var breakfastAdapter: BreakfastAdapter
    private lateinit var lunchRV : RecyclerView
    private lateinit var lunchAdapter: LunchAdapter
    private lateinit var dinnerRV : RecyclerView
    private lateinit var dinnerAdapter: DinnerAdapter
    private lateinit var snackRV : RecyclerView
    private lateinit var snackAdapter: SnackAdapter
    private lateinit var date : String
    private lateinit var breakfastCal : TextView
    private lateinit var lunchCal : TextView
    private lateinit var dinnerCal : TextView
    private lateinit var snackCal : TextView
    private lateinit var today : TextView
    private lateinit var back : TextView
    private lateinit var next : TextView
    private lateinit var email : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_diary, container, false)

        //----------------------Vars-------------------//
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        date = current.format(formatter)
        today = v.findViewById(R.id.currDate)
        back = v.findViewById(R.id.leftDate)
        next = v.findViewById(R.id.rightDate)
        breakfastCal = v.findViewById(R.id.TVBreakfastCalories)
        lunchCal = v.findViewById(R.id.TVLunchCalories)
        dinnerCal = v.findViewById(R.id.TVDinnerCalories)
        snackCal = v.findViewById(R.id.TVSnackCalories)
        breakfast = v.findViewById(R.id.addbreakfast)
        lunch = v.findViewById(R.id.addlunch)
        dinner = v.findViewById(R.id.addDinner)
        snack = v.findViewById(R.id.addSnacks)
        calGoal = v.findViewById(R.id.diaryCalGoal)
        calFood = v.findViewById(R.id.diaryCalFood)
        calRemaining = v.findViewById(R.id.diaryCalRemaining)
        linearLayout = v.findViewById(R.id.llCalories)
        breakfastRV = v.findViewById(R.id.breakfastList)
        lunchRV = v.findViewById(R.id.lunchList)
        dinnerRV = v.findViewById(R.id.dinnerList)
        snackRV = v.findViewById(R.id.snackList)
        breakfastAdapter = BreakfastAdapter()
        lunchAdapter = LunchAdapter()
        dinnerAdapter = DinnerAdapter()
        snackAdapter = SnackAdapter()
        email = auth.currentUser?.email!!
        breakfast.setOnClickListener{
            search("breakfast")
        }
        lunch.setOnClickListener {
            search("lunch")
        }
        dinner.setOnClickListener {
            search("dinner")
        }
        snack.setOnClickListener {
            search("snack")
        }
        linearLayout.setOnClickListener {
            val intent = Intent(this.context, Nutrition::class.java)
            val list = arrayListOf(calFood.text.toString(), calGoal.text.toString())
            val consumed = arrayListOf(breakfastCal.text.toString(), lunchCal.text.toString(), dinnerCal.text.toString(), snackCal.text.toString())
            intent.putStringArrayListExtra("list", consumed)
            intent.putStringArrayListExtra("rem", list)
            startActivity(intent)
        }
        breakfastRV.adapter = breakfastAdapter
        lunchRV.adapter = lunchAdapter
        dinnerRV.adapter = dinnerAdapter
        snackRV.adapter = snackAdapter
        fillFood(date)
        back.setOnClickListener {
            date = (date.toInt() - 1).toString()
            fillFood(date)
        }
        next.setOnClickListener {
            date = (date.toInt() + 1).toString()
            fillFood(date)

        }
        return v
    }
    private fun search(item : String)
    {
        val intent = Intent(this.context, FoodSearch::class.java)
        intent.putExtra("type", item)
        startActivity(intent)
    }

    private fun fillFood(date :String)
    {
        val day = date.substring(date.lastIndex - 1)
        val month = date.substring(date.lastIndex - 3, date.lastIndex - 1)
        val year = date.substring(0, 4)
        Log.d("TAG", "$day $month $year")
        today.text = "$day-$month-$year"
        FoodFiller().fillBreakfast(breakfastAdapter, email, date, breakfastCal)
        FoodFiller().fillLunch(lunchAdapter, email, date, lunchCal)
        FoodFiller().fillDinner(dinnerAdapter, email, date, dinnerCal)
        FoodFiller().fillSnack(snackAdapter, email, date, snackCal)
        setCalories(date)
    }

    private fun setCalories(fetchDate : String)
    {
        db.collection("users").document(email)
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
        db.collection(fetchDate).document(email)
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