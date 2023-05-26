package com.example.remindme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_events.*
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        Continue.setOnClickListener{
            if(checking()) {
                var email = EmailRegister.text.toString()
                var password = PasswordRegister.text.toString()
                var phone = Phone.text.toString()
                var name = Name.text.toString()
                var address = ""
                var gender = ""
                var college = ""



                val user = hashMapOf(
                    "Name" to name,
                    "Phone" to phone,
                    "Email" to email,
                    "Address" to address,
                    "Gender" to gender,
                    "CollegeName" to college
                )

                val Users = db.collection("USERS")

                val query = Users.whereEqualTo("Email", email).get().addOnSuccessListener {
                    tasks->
                    if(tasks.isEmpty) {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() {
                    task->
                    if(task.isSuccessful) {
                        Users.document(email).set(user)
//                        val intent = Intent(this, loggedin::class.java)
//                        intent.putExtra("Email",email)
//                        startActivity(intent)
//                        finish()
                        auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                            Toast.makeText(this,"Please verify your Email", Toast.LENGTH_LONG).show()
                            finish()
                        }
                            ?.addOnFailureListener{
                                Toast.makeText(this,"Invalid Email", Toast.LENGTH_LONG).show()
                            }
                    }
                    else {
                        Toast.makeText(this,"Unsuccessful", Toast.LENGTH_LONG).show()
                    }
                }
                    }
                    else {
                        Toast.makeText(this,"User already exists", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }


            }
            else {
                Toast.makeText(this,"Enter all the details", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checking():Boolean {
        if(Name.text.toString()=="" || Phone.text.toString()=="" || EmailRegister.text.toString()=="" || PasswordRegister.text.toString()=="") {
            return false
        }
        return true
    }
}