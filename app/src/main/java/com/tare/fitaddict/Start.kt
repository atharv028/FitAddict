package com.tare.fitaddict

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.tare.fitaddict.login.Login
import com.tare.fitaddict.login.Signup
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class Start : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_opening)
        auth = FirebaseAuth.getInstance()
        val signup = findViewById<Button>(R.id.BTsignup)
        val login = findViewById<Button>(R.id.BTlogin)
        signup.setOnClickListener{
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val curr = auth.currentUser
        if (curr != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        }
    }
}