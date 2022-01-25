package com.tare.fitaddict.firebase

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.pojo.entities.UserCalories
import com.tare.fitaddict.login.User
import com.tare.fitaddict.pojo.entities.Food
import kotlinx.coroutines.tasks.await
import kotlin.Exception

object FirestoreHelper {
    private const val TAG = "Database"

    suspend fun doesUserExist(email: String): DocumentSnapshot? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("users").document(email)
                .get()
                .await()
        }catch (e: Exception)
        {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error Getting User Details")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", email)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }

    suspend fun newUserAdd(user: User, email: String) {
        val db = FirebaseFirestore.getInstance()
        try {
            db.collection("users").document(email)
                .set(user)
                .await()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error Getting User Details")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", user.email)
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    suspend fun newUserCaloriesSet(calories: UserCalories, date: String, email: String) {
        val db = FirebaseFirestore.getInstance()
        try {
            db.collection(date).document(email)
                .set(calories)
                .await()
        } catch (e: Exception) {
            Log.e(TAG, "Error setting new user's calories", e)
            FirebaseCrashlytics.getInstance().log("Error setting new user's calories")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", email)
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    suspend fun getGoalCalories(email: String): String {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("users").document(email)
                .get()
                .await()
                .get("calorieRequirements")
                .toString()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching calorie requirement for the user", e)
            FirebaseCrashlytics.getInstance().log("Error fetching calorieRequirements")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", email)
            FirebaseCrashlytics.getInstance().recordException(e)
            ""
        }
    }

    suspend fun getCalories(date: String, email: String): UserCalories? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection(date).document(email)
                .get()
                .await()
                .toObject(UserCalories::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting remaining calories", e)
            FirebaseCrashlytics.getInstance().log("Error getting remaining calories")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", email)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }

    suspend fun addFood(food: Food, date: String, email: String, type: String): Boolean {
        val db = FirebaseFirestore.getInstance()
        return try {
            val ref = db.collection(date).document(email)
            ref.collection(type)
                .document(food.foodId)
                .set(food)
                .await()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error adding new food to user", e)
            FirebaseCrashlytics.getInstance().log("Error adding food")
            FirebaseCrashlytics.getInstance().setCustomKey("user id", email)
            FirebaseCrashlytics.getInstance().recordException(e)
            false
        }
    }

    suspend fun updateCalories(updated: UserCalories, date: String, email: String) {
        val db = FirebaseFirestore.getInstance()
        try {
            db.collection(date).document(email)
                .set(updated)
                .await()
        } catch (e: Exception) {
            Log.e(TAG, "Error updating user calories", e)
            FirebaseCrashlytics.getInstance().log("Error updating calories")
            FirebaseCrashlytics.getInstance().setCustomKey("userid", email)
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    suspend fun getFoodItemByType(date: String, email: String, type: String): List<Food> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection(date).document(email)
                .collection(type)
                .get()
                .await()
                .toObjects(Food::class.java)
                .toMutableList()
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving food items", e)
            FirebaseCrashlytics.getInstance().log("Error retrieving food")
            FirebaseCrashlytics.getInstance().setCustomKey("userid", email)
            FirebaseCrashlytics.getInstance().recordException(e)
            listOf()
        }
    }

}