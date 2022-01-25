package com.tare.fitaddict.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.tare.fitaddict.firebase.AuthHelper
import com.tare.fitaddict.firebase.FirestoreHelper
import com.tare.fitaddict.ui.auth.AuthListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    private val disposable = CompositeDisposable()
    private val currentUser = AuthHelper.currentUser()
    var authListener: AuthListener? = null
    val goal: MutableLiveData<String> by lazy { MutableLiveData() }
    val activityLevel: MutableLiveData<String> by lazy { MutableLiveData() }
    val googleClick: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val fbClick: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val height: MutableLiveData<String> by lazy { MutableLiveData() }
    val weight: MutableLiveData<String> by lazy { MutableLiveData() }
    val isComplete: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val doesUserExist: MutableLiveData<Boolean> by lazy { MutableLiveData(null) }
    fun onClickSignUp()
    {

    }

    fun onClickLoginNormal()
    {
        if(email.value?.isEmpty() == true || password.value?.isEmpty() == true)
        {
            authListener?.onFailure("Please enter valid email or password")
            return
        }
        authListener?.onStarted()
        val webService = AuthHelper.loginWithEmailAndPass(email.value!!,password.value!!)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                        authListener?.onSuccess()
            },{
                authListener?.onFailure(it.message!!)
            })
        disposable.add(webService!!)
    }

    fun onClickGoogle()
    {
        googleClick.value = true
    }

    fun onServiceHelper(credential: AuthCredential)
    {
        Log.d("IS CALLED", "CALLED")
        authListener?.onStarted()
        val web = AuthHelper.signInWithServices(credential)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                        authListener?.onSuccess()
            },{
                authListener?.onFailure(it.message!!)
            })
        disposable.add(web!!)
    }

    fun onClickFacebook()
    {
        fbClick.value = true
    }

    fun submitForm()
    {
        if(height.value?.isEmpty() == true || weight.value?.isEmpty() == true)
        {
            return
        }
        isComplete.value = true
    }

    fun validateUserData()
    {
        val email = currentUser?.email
        viewModelScope.launch {
            email?.let {
                val ans =FirestoreHelper.doesUserExist(it)?.exists()
                doesUserExist.value = ans
            }
        }
    }

    fun registerUserToDB()
    {

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}