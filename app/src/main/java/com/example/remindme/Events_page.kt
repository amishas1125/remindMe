package com.example.remindme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_events_page.*
import kotlinx.android.synthetic.main.activity_loggedin.*

class Events_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_page)
        supportActionBar?.hide()

        val btn = findViewById<Button>(R.id.button1)
        btn.setOnClickListener {
            val Intent = Intent(this, ADD_EVENTS::class.java)
            startActivity(Intent)
        }


        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin = sharedPref.getString("Email", "1")

        logout1.setOnClickListener{
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        selectfile.setOnClickListener {
            var intent = Intent(this, SelectFile::class.java)
            startActivity(intent)
        }

        vieweventbtn.setOnClickListener{
            val Intent = Intent(this, viewEvents::class.java)
            startActivity(Intent)
        }
    }
}