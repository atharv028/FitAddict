package com.tare.fitaddict.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.R


class Signup : AppCompatActivity() {
    private lateinit var name : EditText
    private lateinit var age : EditText
    private lateinit var sex : RadioGroup
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var btn : Button
    private lateinit var fbtn: LoginButton
    private lateinit var auth : FirebaseAuth
    private lateinit var googlebtn : SignInButton
    private lateinit var db : FirebaseFirestore
    private val callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //--------------VARS-----------------//
        auth = FirebaseAuth.getInstance()
        googlebtn = findViewById(R.id.googleSign)
        name = findViewById(R.id.ETname)
        age = findViewById(R.id.ETage)
        sex = findViewById(R.id.RGsex)
        email = findViewById(R.id.ETemail)
        password = findViewById(R.id.ETpass)
        btn = findViewById(R.id.BTsubmit)
        fbtn = findViewById(R.id.fbSign)
        db = FirebaseFirestore.getInstance()
        //----------------------------------//

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("603746605086-1tddusft9jn53g3jj0g1ug4ss1th5ulb.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        btn.setOnClickListener {
            if(name.editableText.isEmpty() or age.editableText.isEmpty() or (sex.checkedRadioButtonId == -1) or email.editableText.isEmpty() or password.editableText.isEmpty())
            {
                Toast.makeText(this,"Please Fill the entire form", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            normal(email.editableText.toString(), password.editableText.toString())
        }

        fbtn.setOnClickListener {
            fb(fbtn)
        }
        googlebtn.setOnClickListener {
            signIn(googleSignInClient)
        }

    }

    private fun signIn(googlesignInClient: GoogleSignInClient) {
        val signInIntent: Intent = googlesignInClient.signInIntent
        startActivityForResult(signInIntent, 2)
    }

    private fun normal(email: String, password: String)
    {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful)
                {
                    val user = auth.currentUser
                    Log.d("TAG", "Successfully Created User ${name.editableText}")
                    goto(user?.email!!)
                }
                else
                {
                    Log.w("TAG", "Failed")
                }
            }
    }

    private fun fb(buttonFacebookLogin : LoginButton)
    {
        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d("TAG", "facebook:onSuccess:$result")
                val user = auth.currentUser
                goto(user?.email!!)
            }

            override fun onCancel() {
                Log.d("TAG", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("TAG", "facebook:onError", error)
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2)
        {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("TAG", "FirebaseauthwithGoogle : " + account?.id)
                val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { cred ->
                        if (cred.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success")
                            val user = auth.currentUser!!
                            goto(user.email!!)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.exception)
                        }
                    }
            }catch (e : ApiException)
            {
                Log.d("TAG", "Google Failed")
            }
        }
    }
    private fun goto(email : String)
    {
        val temp = sex.checkedRadioButtonId
        val gender = findViewById<RadioButton>(temp)
        val intent = Intent(this, Form::class.java)
        intent.putExtra("info", arrayListOf(email, age.editableText.toString(), gender.text, name.editableText.toString()))
        startActivity(intent)
    }
}