package com.example.rama.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rama.MainActivity
import com.example.rama.R
import com.google.firebase.auth.FirebaseAuth

class emptyActivity : AppCompatActivity() {

    private val mAuth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mAuth.currentUser != null){
            goToMainActivity()
        }else{
            goToLogInActivity()
        }
    }

        private fun goToLogInActivity() {
            val intent= Intent(this,logInActivity::class.java)
            startActivity(intent)
            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        fun goToMainActivity(){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

    }