package com.example.remindme

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide(); // hides action bar
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,loggedin::class.java )
            startActivity(intent)
            finish()
        },2000)
    }
}