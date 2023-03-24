package com.example.remindme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_events.*
import java.util.*

class ADD_EVENTS : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore

    private var uid = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_events)




        add_event.setOnClickListener {


            var id = uid+1
            uid=uid+1
            var email = intent.getStringExtra("Email")
            var ename = name_id.text.toString()
            var edesc = descid.text.toString()
            var eloc= location_id.text.toString()

            val event = hashMapOf(
                "Event ID" to id,
                "Email" to email,
                "Event Name" to ename,
                "Description" to edesc,
                "Location" to eloc
            )

            val Events = db.collection("EVENTS").document(id.toString()).set(event)


            val startMillis: Long = Calendar.getInstance().run {
                set(2012, 0, 19, 7, 30)
                timeInMillis
            }
            val endMillis: Long = Calendar.getInstance().run {
                set(2012, 0, 19, 8, 30)
                timeInMillis
            }
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                .putExtra(CalendarContract.Events.TITLE, ename)
                .putExtra(CalendarContract.Events.DESCRIPTION, edesc)
                .putExtra(CalendarContract.Events.CALENDAR_ID,id)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, eloc)

                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)

            startActivity(intent)
        }


    }
}