package com.tare.fitaddict.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.tare.fitaddict.MainActivity
import com.tare.fitaddict.R
import com.tare.fitaddict.Start
import com.tare.fitaddict.databinding.FragmentLoginBinding
import com.tare.fitaddict.ui.auth.AuthListener
import kotlin.math.log

@BindingAdapter("android:onClick")
fun bindGoogle(button: SignInButton, method: () -> Unit)
{
    button.setOnClickListener { method.invoke() }
}

class LoginFragment : Fragment(), AuthListener {
    private val loginViewModel by viewModels<LoginViewModel>()
    private var googleSignInClient: GoogleSignInClient? = null
    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.apply {
            viewModel = loginViewModel
            lifecycleOwner = this@LoginFragment
        }
        loginViewModel.authListener = this
        attachObservers()
        return binding.root
    }

    private fun initGoogleSignInClient()
    {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("603746605086-1tddusft9jn53g3jj0g1ug4ss1th5ulb.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signInUsingGoogle()
    {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 2)
    }

    private fun attachObservers() {
        loginViewModel.googleClick.observe(viewLifecycleOwner){
            if(it == true)
            {
                initGoogleSignInClient()
                signInUsingGoogle()
            }
        }

//        loginViewModel.fbClick.observe(viewLifecycleOwner){
//            if(it == true)
//            {
//                fb()
//            }
//        }
        loginViewModel.doesUserExist.observe(viewLifecycleOwner){
            if(it == true)
            {
                startActivity(Intent(requireActivity(),MainActivity::class.java))
                (requireActivity() as Start).finish()
            }else if(it == false)
            {
                (requireActivity() as Start).replaceFragment(FormFragment())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null)
                    getGoogleAuthCredential(account)
            }catch (e : ApiException)
            {
                Log.w("TAG", "Google Sign In Failed", e.cause)
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun getGoogleAuthCredential(account: GoogleSignInAccount)
    {
        val token = account.idToken
        val credential = GoogleAuthProvider.getCredential(token, null)
        loginViewModel.onServiceHelper(credential)
    }

    private fun fb(buttonFacebookLogin : LoginButton)
    {
        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d("TAG", "facebook:onSuccess:$result")
            }

            override fun onCancel() {
                Log.d("TAG", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("TAG", "facebook:onError", error)
            }
        })
    }

    override fun onStarted() {
        Log.d("CAN BE CALLED", "STARTED")
        Toast.makeText(context, "Login Started", Toast.LENGTH_LONG).show()
    }

    override fun onSuccess() {
        Log.d("CAN BE CALLED", "SUCCESS")
        Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
        loginViewModel.validateUserData()
    }

    override fun onFailure(message: String) {
        Log.d("CAN BE CALLED", "ERROR $message")
        Toast.makeText(context, "Login failed: $message", Toast.LENGTH_LONG).show()
    }
}