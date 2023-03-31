package com.example.remindme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_events_page.*

class Events_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_page)

        val btn = findViewById<Button>(R.id.button1)
        btn.setOnClickListener {
            val Intent = Intent(this, ADD_EVENTS::class.java)
            startActivity(Intent)
        }

        updatebtn.setOnClickListener{
            val Intent = Intent(this, update::class.java)
            startActivity(Intent)
//            val eventID: Long = 208
//            val uri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID)
//            val intent = Intent(Intent.ACTION_EDIT)
//                .setData(uri)
//                .putExtra(CalendarContract.Events.TITLE, "My New Title")
//            startActivity(intent)
        }
    }
}