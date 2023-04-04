package com.example.remindme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_delete_event.*

class deleteEvent : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_event)

        finaldel.setOnClickListener {
            var delevename = deleventname.text.toString()

            if(delevename=="") {
                Toast.makeText(this,"Please Enter the event name", Toast.LENGTH_SHORT).show()
            }

            else {
                auth = FirebaseAuth.getInstance()
                db = FirebaseFirestore.getInstance()

                var useremail = Firebase.auth.currentUser?.email

                db.collection("EVENTS").document(delevename).delete().addOnSuccessListener {
                    Toast.makeText(this,"Deleted successfully", Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(this,"No such event", Toast.LENGTH_SHORT).show()
                    }


            }
        }
    }
}