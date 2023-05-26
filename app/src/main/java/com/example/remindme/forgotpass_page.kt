package com.example.remindme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgotpass_page.*

class forgotpass_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpass_page)
        supportActionBar?.hide()

        submitforgot.setOnClickListener{
            val email = emailforgot.text.toString()

            if(email=="") {
                Toast.makeText(this,"Please enter the Email address",Toast.LENGTH_LONG).show()
            }
            else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener() {
                    task->
                    if(task.isSuccessful) {
                        Toast.makeText(this,"Check your Email to reset your password",Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this,"Email invalid",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}