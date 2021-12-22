package com.tare.fitaddict.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tare.fitaddict.MainActivity
import com.tare.fitaddict.R

class Login : AppCompatActivity() {
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var button : Button
    private lateinit var google : SignInButton
    private lateinit var  fb : LoginButton
    private lateinit var auth: FirebaseAuth
    private val callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        //-------------------------Variables--------------------//
        email = findViewById(R.id.ETlogEmail)
        password = findViewById(R.id.ETlogPass)
        button = findViewById(R.id.BTlogSubmit)
        google = findViewById(R.id.googleLog)
        fb = findViewById(R.id.fbLog)

        //-------------------------Google----------------------//
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("603746605086-1tddusft9jn53g3jj0g1ug4ss1th5ulb.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googlesignInClient = GoogleSignIn.getClient(this, gso)

        google.setOnClickListener {
            signIn(googlesignInClient)
        }

        //------------------------Normal--------------------//
        button.setOnClickListener {
            if (email.editableText.isEmpty() or password.editableText.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            normal(email.editableText.toString(), password.editableText.toString())
        }

        // ---------------------Facebook-------------------//
        fb.setOnClickListener {
            fb(fb)
        }


    }

    private fun normal(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "Sign In Successful")
                    goto()
                }
                else
                {
                    Log.w("TAG", "Failed (email)", task.exception)
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
                    return@addOnCompleteListener
                }
            }
    }

    private fun signIn(googleSignInClient: GoogleSignInClient)
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
                Log.d("TAG", "firebase auth with Google :" + account?.id)
                val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful)
                        {
                            Log.d("TAG", "Sign In With Google : Success")
                            goto()
                        }
                        else
                        {
                            Log.w("TAG", "Sign In With Google: Failed", task.exception)
                        }
                    }
            }catch (e : ApiException)
            {
                Log.w("TAG", "Google Sign In Failed", e.cause)
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
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