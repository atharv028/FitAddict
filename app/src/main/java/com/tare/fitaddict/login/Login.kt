package com.tare.fitaddict.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tare.fitaddict.MainActivity
import com.tare.fitaddict.R
import com.tare.fitaddict.UserRepository
import com.tare.fitaddict.databinding.ActivityLoginBinding
import com.tare.fitaddict.firebase.AuthHelper
import com.tare.fitaddict.ui.auth.LoginViewModel

class Login : AppCompatActivity() {
    private lateinit var  fb : LoginButton
    private lateinit var auth: FirebaseAuth
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var googleSignInClient: GoogleSignInClient
    val mainViewModel: LoginViewModel = LoginViewModel(repository = UserRepository(firebase = AuthHelper))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.apply {
            Log.d("sfd", "Called")
            lifecycleOwner = this@Login
            viewModel = mainViewModel
        }
        auth = FirebaseAuth.getInstance()
        //-------------------------Variables--------------------//
        val google = findViewById<SignInButton>(R.id.googleLog)
        fb = findViewById(R.id.fbLog)

        //-------------------------Google----------------------//
        initGoogleSignInClient()

        google.setOnClickListener {
            signInUsingGoogle()
        }
//        // ---------------------Facebook-------------------//
//        fb.setOnClickListener {
//            fb(fb)
//        }


    }

    private fun initGoogleSignInClient()
    {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("603746605086-1tddusft9jn53g3jj0g1ug4ss1th5ulb.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signInUsingGoogle()
    {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 2)
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
        mainViewModel.loginWithGoogle(credential)
    }

    private fun fb(buttonFacebookLogin : LoginButton)
    {
        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d("TAG", "facebook:onSuccess:$result")
                goto()
            }

            override fun onCancel() {
                Log.d("TAG", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("TAG", "facebook:onError", error)
            }
        })
    }

    private fun goto()
    {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        finish()
    }

}