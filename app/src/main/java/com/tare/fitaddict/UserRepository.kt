package com.tare.fitaddict

import com.google.firebase.auth.AuthCredential
import com.tare.fitaddict.firebase.AuthHelper

class UserRepository (
    private val firebase: AuthHelper
)
{
    fun providerSignIn(credential: AuthCredential) = firebase.signInWithServices(credential)
    fun login(email: String, password: String) = firebase.loginWithEmailAndPass(email, password)
    fun register(email: String, password: String) = firebase.registerWithEmailAndPass(email, password)
    fun currentUser() = firebase.currentUser()
    fun logout() = firebase.logout()
}
