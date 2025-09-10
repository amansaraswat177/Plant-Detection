package com.example.plantdetection

import ImageAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class WelcomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        val dBtn = findViewById<Button>(R.id.dbtn)
        val ecomBtn = findViewById<Button>(R.id.eom_btn)
        val prevBtn = findViewById<ImageButton>(R.id.prev_btn)
        val nextBtn = findViewById<ImageButton>(R.id.next_btn)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val images = listOf(R.drawable.photo2, R.drawable.photo1, R.drawable.photo3) // Your images

        val adapter = ImageAdapter(images)
        viewPager.adapter = adapter

        // Auto slide images every 3 seconds
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (viewPager.currentItem == images.size - 1) {
                    viewPager.setCurrentItem(0, true)
                } else {
                    viewPager.setCurrentItem(viewPager.currentItem + 1, true)
                }
                handler.postDelayed(this, 3000) // 3 seconds delay
            }
        }
        handler.postDelayed(runnable, 3000)

        // Button for disease page navigation
        dBtn.setOnClickListener {
            Log.d("WelcomePage", "dBtn button clicked")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Button for ecommerce page navigation
        ecomBtn.setOnClickListener {
            Log.d("WelcomePage", "Ecom button clicked")
            val intent = Intent(this, ecompage::class.java)
            startActivity(intent)
        }

        // Edge to Edge UI
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Previous and Next Button Functionality
        prevBtn.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            }
        }

        nextBtn.setOnClickListener {
            if (viewPager.currentItem < images.size - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            }
        }
    }
}