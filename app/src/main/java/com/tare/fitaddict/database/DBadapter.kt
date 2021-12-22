package com.tare.fitaddict.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DBadapter {
    private lateinit var db : FirebaseFirestore
    fun init()
    {
        db = FirebaseFirestore.getInstance()
    }
}