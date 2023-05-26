package com.example.remindme

import android.content.Intent
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.util.CollectionUtils.mapOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed.*
import java.util.*

class DetailedActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        supportActionBar?.hide()

        val info = intent.getParcelableExtra<Users>("Info")

        if(info!=null) {
            val detname: TextView= findViewById(R.id.detailedName)
            val detdesc: EditText = findViewById(R.id.detailedDesc)
            val detloc: EditText = findViewById(R.id.detailedLoc)

            detname.text = info.EventName
            detdesc.setText(info.Description)
            detloc.setText(info.Location)

        }

        detailedUpdate.setOnClickListener {

            var docname = info?.EventName
            var updatename = detailedName.text.toString()
            var updatedesc = detailedDesc.text.toString()
            var updateloc = detailedLoc.text.toString()

            if(updatename=="" || updatedesc=="" || updateloc=="") {
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
            else {
                auth = FirebaseAuth.getInstance()
                db = FirebaseFirestore.getInstance()

                val new = mapOf(
                    "Description",updatedesc,
                    "Location",updateloc,
                    "EventName",updatename
                )

                if (docname != null) {
                    db.collection("EVENTS").document(docname).update(new as Map<String,Any>)
                        .addOnSuccessListener {
                            Toast.makeText(this,"Successfully updated",Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{
                            Toast.makeText(this,"Update unsuccessful",Toast.LENGTH_SHORT).show()
                        }
                }

                var intent = Intent(this, viewEvents::class.java)
                startActivity(intent)
                finish()
            }

        }

        detailedDelete.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            db = FirebaseFirestore.getInstance()


            var docname = detailedName.text.toString()


                db.collection("EVENTS").document(docname).delete().addOnSuccessListener {
                    Toast.makeText(this,"Deleted successfully",Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(this,"Delete unsuccessful",Toast.LENGTH_SHORT).show()
                    }

            var intent = Intent(this, viewEvents::class.java)
            startActivity(intent)
            finish()

        }
    }
}