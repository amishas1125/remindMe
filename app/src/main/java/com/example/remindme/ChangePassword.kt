package com.example.remindme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        supportActionBar?.hide()

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)?:return



//        val newpass1 = passchange1.text.toString()
//        val newpass2 = passchange2.text.toString()

        changepassbtn.setOnClickListener{

            if(passchange1.text.toString()=="" || passchange2.text.toString()=="") {
                Toast.makeText(this, "Please enter all the fields",Toast.LENGTH_SHORT).show()
            }
            else {

                var newpass1 = passchange1.text.toString()
                var newpass2 = passchange2.text.toString()
                var current = currpass.text.toString()

                if(newpass1!=newpass2) {
                    Toast.makeText(this, "Passwords do not match",Toast.LENGTH_SHORT).show()
                }
                else {

                    auth = FirebaseAuth.getInstance()
                    val user = Firebase.auth.currentUser
                    val useremail = user?.email

                    if(useremail!=null) {
                        val credential = EmailAuthProvider.getCredential(useremail,current)
                        if (user != null) {
                            user.reauthenticate(credential).addOnCompleteListener{
                                Toast.makeText(this, "Reauthentication successful",Toast.LENGTH_SHORT).show()
                            }
                        }

                        user!!.updatePassword(newpass1).addOnCompleteListener{
                                task->
                            if(task.isSuccessful) {
                                Toast.makeText(this, "Password changed successfully",Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(this, "Password changed unsuccessful",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            }
        }

        logoutreset.setOnClickListener {
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}