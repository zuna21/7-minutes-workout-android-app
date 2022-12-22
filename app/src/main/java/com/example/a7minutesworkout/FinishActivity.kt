package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarFinish)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolBarFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}