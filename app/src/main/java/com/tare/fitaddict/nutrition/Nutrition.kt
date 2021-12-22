package com.tare.fitaddict.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.tare.fitaddict.MainActivity
import com.tare.fitaddict.R

class Nutrition : AppCompatActivity() {
    private lateinit var tabs : TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)
        tabs = findViewById(R.id.TLnutri)
        val list = intent.getStringArrayListExtra("list")!!
        val remaining = intent.getStringArrayListExtra("rem")!!
        val calories = Calories.newInstance(list, remaining)
        val macros = Macros()
        setCurrentFragment(calories)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position)
                {
                    0 -> {
                        setCurrentFragment(calories)
                    }
                    1 -> setCurrentFragment(macros)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FLnutrition,fragment)
            commit()
        }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}