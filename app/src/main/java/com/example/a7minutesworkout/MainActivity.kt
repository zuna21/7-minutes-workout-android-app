package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flStartButton: FrameLayout = findViewById(R.id.fl_start_button)
        flStartButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "You click it", Toast.LENGTH_LONG).show()
        }
    }
}