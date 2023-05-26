package com.example.remindme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.util.CollectionUtils
import com.google.android.gms.common.util.CollectionUtils.mapOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)?:return

        var email = Firebase.auth.currentUser?.email

        if(email != null) {
            db.collection("USERS").document(email).get().addOnSuccessListener {
                tasks->
//                editemail.text = tasks.get("Email").toString()
                editName.setText(tasks.get("Name").toString())
                editPhone.setText(tasks.get("Phone").toString())
                if(tasks.get("Address").toString()!="") {
                    addressfield.setText(tasks.get("Address").toString())
                }
                else {
                    addressfield.setText("")
                }
                if(tasks.get("Gender").toString()!="") {
                    gendertext.setText(tasks.get("Gender").toString())
                }
                else {
                    gendertext.setText("")
                }
                if(tasks.get("CollegeName").toString()!="") {
                    collegetext.setText(tasks.get("CollegeName").toString())
                }
                else {
                    collegetext.setText("")
                }
            }
        }

        editbtn.setOnClickListener {
            if(editName.text.toString() == "" || editPhone.text.toString() == "") {
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
            else {
                var newName = editName.text.toString()
                var newPhone = editPhone.text.toString()
                var address = addressfield.text.toString()
                var gender = gendertext.text.toString()
                var college = collegetext.text.toString()


                if(email!=null) {
                    val new = mapOf(
                        "Email", email,
                        "Name", newName,
                        "Phone", newPhone,
                        "Address", address,
                        "Gender", gender,
                        "CollegeName", college
                    )

                    db.collection("USERS").document(email).update(new as Map<String, Any>).addOnSuccessListener {
                        Toast.makeText(this,"Successfully updated",Toast.LENGTH_SHORT).show()
                        sharedPref.edit().remove("Email").apply()
                        var intent = Intent(this, loggedin::class.java)
                        startActivity(intent)
                        finish()
                    }
                        .addOnFailureListener{
                            Toast.makeText(this,"Unsuccessful",Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        logoutedit.setOnClickListener {
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}