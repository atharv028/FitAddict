package com.tare.fitaddict.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.tare.fitaddict.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var email: String? = null
    var password: String? = null
    val Tag: String = LoginViewModel::class.java.simpleName;
    private var authListener: AuthListener? = null

    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }


    fun login() {
        Log.d("TAG", "ViewModel Function Called")
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        authListener?.onStarted()

        val disposable = repository.login(email!!, password!!)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable!!)
    }

    fun loginWithGoogle(credential: AuthCredential) {
        authListener?.onStarted()
        val disposable = repository.providerSignIn(credential)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable!!)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}