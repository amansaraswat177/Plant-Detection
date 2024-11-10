package com.example.plantdetection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WelcomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        val dBtn = findViewById<Button>(R.id.dbtn)
        val ecomBtn = findViewById<Button>(R.id.eom_btn)

// Disease page or goto main activity intent
        dBtn.setOnClickListener {
            Log.d("WelcomePage", "dBtn button clicked")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // goto Ecommerce page intent
        ecomBtn.setOnClickListener {
            Log.d("WelcomePage", "Ecom button clicked")
            val intent = Intent(this, ecompage::class.java)
            startActivity(intent)
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
