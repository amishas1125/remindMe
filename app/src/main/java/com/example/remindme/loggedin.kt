package com.example.remindme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_loggedin.*

class loggedin : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loggedin)
        supportActionBar?.hide()

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin = sharedPref.getString("Email", "1")

        logout.setOnClickListener{
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        changepass.setOnClickListener{
            var intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }

        editProfile.setOnClickListener {
            var intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }


        if(isLogin=="1") {
            var email = intent.getStringExtra("Email")
            if(email != null) {
                setText(email)
//                with(sharedPref.edit()) {
//                    putString("Email", email)
//                    apply()
//                }
                val editor = sharedPref.edit()
                editor.putString("Email",email)
                editor.apply()
            }
            else {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else {
            setText(isLogin)
        }

        val btn=findViewById<Button>(R.id.event_btn)
        btn.setOnClickListener{

            val Intent = Intent(this,Events_page::class.java)
            startActivity(Intent)
        }


    }

    private fun setText(email: String?) {
        db = FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get().addOnSuccessListener { tasks->
                name.text = tasks.get("Name").toString()
                emaillog.text = tasks.get("Email").toString()
            }
        }
    }
}