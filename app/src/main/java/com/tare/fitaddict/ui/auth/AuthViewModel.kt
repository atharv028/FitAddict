package com.tare.fitaddict.ui.auth

import androidx.lifecycle.ViewModel
import com.tare.fitaddict.UserRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AuthViewModel(
    private val repository: UserRepository
): ViewModel() {
    var email: String? = null
    var password: String? = null
    var authListener : AuthListener? = null
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }


}