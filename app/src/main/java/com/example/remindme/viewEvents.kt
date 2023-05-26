package com.example.remindme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class viewEvents : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var usersList: ArrayList<Users>
//    private lateinit var userAdapter: UserAdaptor
    private var db = Firebase.firestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_events)
        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        usersList = arrayListOf()
        var userAdapter = UserAdaptor(usersList)

        var email = Firebase.auth.currentUser?.email.toString()

        db.collection("EVENTS").whereEqualTo("Email",email).get()
            .addOnSuccessListener {
                if(!it.isEmpty) {
                    for(data in it.documents) {
                        val user: Users? = data.toObject<Users>(Users::class.java)
                        usersList.add(user!!)
                    }
                    recyclerView.adapter = userAdapter

                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }




        userAdapter.onItemClick = {

            val intent = Intent(this, DetailedActivity::class.java)
            intent.putExtra("Info", it)
            startActivity(intent)
            finish()
        }


    }
}