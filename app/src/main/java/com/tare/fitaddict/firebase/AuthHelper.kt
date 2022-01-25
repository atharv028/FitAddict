package com.tare.fitaddict.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.core.Completable


object AuthHelper {
    var isNew = false
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun signInWithServices(authCredential: AuthCredential) : Completable? = Completable.create { emitter ->
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener {
                if(!emitter.isDisposed)
                {
                    if(it.isSuccessful){
                        isNew = it.result?.additionalUserInfo?.isNewUser!!
                        emitter.onComplete()
                    }
                    else
                        emitter.onError(it.exception!!)
                }
            }
    }


    fun loginWithEmailAndPass(email: String, password: String): Completable? = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful)
                        emitter.onComplete()
                    else
                        emitter.onError(it.exception!!)
                }
            }

    }

    fun registerWithEmailAndPass(email: String, password: String): Completable? = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful){
                        isNew = true
                        emitter.onComplete()
                    }
                    else
                        emitter.onError(it.exception!!)
                }
            }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser
}