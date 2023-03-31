package com.example.remindme

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Toast
import com.google.android.gms.common.util.CollectionUtils.mapOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_update.*
import java.util.*

class update : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        done.setOnClickListener {
            var uname = updatename.text.toString()
            var udesc = updatedesc.text.toString()
            var uloc = updateloc.text.toString()

            if(uname == "" || udesc =="" || uloc =="") {
                Toast.makeText(this, "Enter all the fields", Toast.LENGTH_LONG).show()
            }

            else {
                auth = FirebaseAuth.getInstance()
                db = FirebaseFirestore.getInstance()

                var user = Firebase.auth.currentUser?.email
//
                val new = mapOf(
//                "Event ID" to id,
                    "Description",udesc,

                   "Location",uloc,
                    "EventName",uname
                )

                db.collection("EVENTS").document(uname).update(new as Map<String, Any>).addOnSuccessListener {
                    Toast.makeText(this,"Successfully updated",Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener{
                        Toast.makeText(this,"Unsuccessful",Toast.LENGTH_SHORT).show()
                    }


//                val eventID = CalendarContract.Events.CALENDAR_ID
//                val uri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID)
//            val intent = Intent(Intent.ACTION_EDIT)
//                .setData(uri)
//                .putExtra(CalendarContract.Events.TITLE, "My New Title")
//            startActivity(intent)
            }
        }
    }
}