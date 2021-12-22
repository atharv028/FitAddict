package com.tare.fitaddict.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.MainActivity
import com.tare.fitaddict.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.floor

class Form : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val date = current.format(formatter)
        val goal = findViewById<Spinner>(R.id.spinGoal)
        val activity = findViewById<Spinner>(R.id.spinActivity)
        val height = findViewById<EditText>(R.id.ETheight)
        val weight = findViewById<EditText>(R.id.ETweight)
        val submit = findViewById<Button>(R.id.BTform)
        val db = FirebaseFirestore.getInstance()
        val info = intent.getStringArrayListExtra("info")!!
        val goalAdapter = ArrayAdapter.createFromResource(this, R.array.arrGoal, android.R.layout.simple_spinner_item)
        val activityAdapter = ArrayAdapter.createFromResource(this, R.array.arrActivity, android.R.layout.simple_spinner_item)
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        goal.adapter = goalAdapter
        activity.adapter = activityAdapter
        submit.setOnClickListener {
            if(height.editableText.isEmpty() or weight.editableText.isEmpty())
            {
                Toast.makeText(this, "Please fill out height and Weight", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            var bmr = 0.0
            if(info[2] == "Male")
                bmr = 10*weight.editableText.toString().toInt() + 6.25*height.editableText.toString().toInt()- 5 * info[1].toInt() + 5
            else
                bmr = 10*weight.editableText.toString().toInt() + 6.25*height.editableText.toString().toInt()- 5 * info[1].toInt() - 161

            var calorieRequirement = 0.0
            if(activity.selectedItem == "Not Very Active")
            {
                calorieRequirement = 1.2*bmr
            }
            else if(activity.selectedItem == "Lightly Active")
            {
                calorieRequirement = 1.375*bmr
            }
            else if(activity.selectedItem == "Active")
            {
                calorieRequirement = 1.55*bmr
            }else
            {
                calorieRequirement = 1.9*bmr
            }
            val user = User(info[0], info[3] ,info[1].toInt(), info[2],goal.selectedItem.toString(), activity.selectedItem.toString()
            ,height.editableText.toString(),weight.editableText.toString(),floor(calorieRequirement).toInt())
            Log.d("TAG", info[0])
            db.collection("users").document(info[0])
                .set(user)
                .addOnSuccessListener {
                    Log.d("TAG", "added goal with")
                    goto()
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
            val cal = hashMapOf<String, Long>()
            cal["consumedCalories"] = 0
            cal["remainingCalories"] = (floor(calorieRequirement).toInt()).toLong()
            db.collection(date).document(info[0])
                .set(cal)
                .addOnSuccessListener {
                    Log.d("TAG", "Set consumed as 0")
                }
                .addOnFailureListener {
                    Log.w("TAG", "Failed to set consumed as 0 because :", it)
                }
        }
    }

    private fun goto()
    {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        finish()
    }

}