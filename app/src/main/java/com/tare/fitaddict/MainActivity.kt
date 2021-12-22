package com.tare.fitaddict

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.diary.DiaryFragment
import com.tare.fitaddict.home.HomeFragment
import com.tare.fitaddict.recipes.RecipesFragment
import com.tare.fitaddict.workout.WorkoutFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val home = HomeFragment()
        val diary = DiaryFragment()
        val workout = WorkoutFragment()
        val recipes = RecipesFragment()
        db = FirebaseFirestore.getInstance()
        setCurrentFragment(home)
        auth = FirebaseAuth.getInstance()
        bottomNavigation = findViewById(R.id.bottomNav)
        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.home -> {
                    setCurrentFragment(home)
                }
                R.id.diary -> {
                    setCurrentFragment(diary)
                }
                R.id.workout -> {
                    setCurrentFragment(workout)
                }
                R.id.recipes -> {
                    setCurrentFragment(recipes)
                }
            }
            return@setOnItemSelectedListener true
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.mybutton)
        {
            MaterialAlertDialogBuilder(this)
                .setTitle("Do you Want To Log Out?")
                .setNegativeButton("No"){ dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton("Yes"){ _, _ ->
                    auth.signOut()
                    val intent = Intent(this, Start::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    finish()
                }
                .show()
        }
        return super.onOptionsItemSelected(item)
    }
}