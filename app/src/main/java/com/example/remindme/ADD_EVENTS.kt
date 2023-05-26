package com.example.remindme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Toast
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_events.*
import kotlinx.android.synthetic.main.activity_events_page.*
import kotlinx.android.synthetic.main.activity_loggedin.*
import java.util.*

class ADD_EVENTS : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

//    private var uid = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_events)
        supportActionBar?.hide()

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin = sharedPref.getString("Email", "1")

        logout2.setOnClickListener{
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }




        add_event.setOnClickListener {


//            var id = uid+1
//            uid += 1
            db = FirebaseFirestore.getInstance()
            auth = FirebaseAuth.getInstance()

            val curuser = Firebase.auth.currentUser

            if(curuser!= null) {
                var email = curuser.email.toString()
                var ename = name_id.text.toString()
                var edesc = descid.text.toString()
                var eloc = location_id.text.toString()

                if (ename == "" || edesc == "" || eloc == "") {
                    Toast.makeText(this, "Enter all fields", Toast.LENGTH_LONG).show()
                }

                else {

                val query = db.collection("EVENTS").whereEqualTo("EventName",ename).whereEqualTo("Email", email).get()
                    .addOnSuccessListener {
                        tasks->
                        if(tasks.isEmpty) {
                            val event = hashMapOf(
//                "Event ID" to id,
                                "Email" to email,
                                "EventName" to ename,
                                "Description" to edesc,
                                "Location" to eloc
                            )

                            val Events = db.collection("EVENTS").document(ename).set(event)

//                var cnt = db.collection("EVENTS").count()


//                var id =

//                val ref = db.collection("EVENTS").document().id


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
                                .putExtra(CalendarContract.Events.CALENDAR_ID, ename)
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, eloc)

                                .putExtra(
                                    CalendarContract.Events.AVAILABILITY,
                                    CalendarContract.Events.AVAILABILITY_BUSY
                                )

                            startActivity(intent)
                        }

                        else {
                            Toast.makeText(this,"Event name already exists",Toast.LENGTH_SHORT).show()
                        }
                    }


            }
            }

//            var email = intent.getStringExtra("Email")



        }


    }
}