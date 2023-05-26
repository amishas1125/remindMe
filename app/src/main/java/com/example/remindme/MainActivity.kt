package com.example.remindme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.trimmedLength
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        auth= FirebaseAuth.getInstance()

        Register.setOnClickListener{
            var intent = Intent(this,register::class.java)
            startActivity(intent)
            //finish()
        }

        forgotpass.setOnClickListener{
            var intent = Intent(this,forgotpass_page::class.java)
            startActivity(intent)
            //finish()
        }

        Login.setOnClickListener{
            if(checking()) {
                val email = Email.text.toString()
                val password = Password.text.toString()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){ task->
                        if(task.isSuccessful) {

                           var verify= auth.currentUser?.isEmailVerified

                            if(verify==true)
                            {
                                var intent = Intent(this,loggedin::class.java)
                                intent.putExtra("Email",email)
                                startActivity(intent)
                                finish()
                            }
                            else {
                                Toast.makeText(this, "Please verify your email", Toast.LENGTH_LONG).show()
                            }

                        }
                        else {
                            Toast.makeText(this, "Unsuccessful", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else {
                Toast.makeText(this, "Enter the details", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun checking(): Boolean {
        if(Email.text.toString() == "" || Password.text.toString() == "") return false
        return true
    }

}