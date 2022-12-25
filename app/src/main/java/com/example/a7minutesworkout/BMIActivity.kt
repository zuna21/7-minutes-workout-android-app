package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.core.view.isEmpty
import com.example.a7minutesworkout.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    var inputHeight: String? = null
    var inputWeight: String? = null

    var BMIResult: String? = null
    var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBMI)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }

        binding?.toolbarBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            showBMIResults()
        }

        binding?.rbUsUnits?.setOnCheckedChangeListener { button, b ->
            Toast.makeText(this@BMIActivity, button.text, Toast.LENGTH_SHORT).show()
        }
    }



    private fun showBMIResults() {
        val height: String = binding?.otfHeight?.editText?.text.toString()
        val weight: String = binding?.otfWeight?.editText?.text.toString()

        if(height.isEmpty() || weight.isEmpty()) {
            Toast.makeText(this@BMIActivity, "Please Enter weight or height",
                Toast.LENGTH_SHORT).show()
        } else {
            calculateBMI(height, weight)
            binding?.tvYourBMI?.visibility = View.VISIBLE
            binding?.tvBMIInNumber?.text = BMIResult!!.toString()
            binding?.tvBMIInNumber?.visibility = View.VISIBLE
            binding?.tvMessage?.text = message!!
            binding?.tvMessage?.visibility = View.VISIBLE
        }
    }

    private fun calculateBMI(height: String, weight: String){
        val heightNum: Int? = height.toIntOrNull()
        val weightNum: Int? = weight.toIntOrNull()

        if(heightNum == null || weightNum == null){
            Toast.makeText(this@BMIActivity, "Please Enter a valid number",
                Toast.LENGTH_SHORT).show()
        } else {
            val heightInMeters: Double = heightNum.toDouble() / 100
            val result: Double = weightNum.toDouble() / (heightInMeters * heightInMeters)

            if(result <= 18.5) {
                BMIResult = "Underweight"
                message = "You really need to eat more man. You are starving like a bitch"
            } else if (result > 18.5 && result < 24.9) {
                BMIResult ="Healthy Weight"
                message = "Oho, what a healthy boy... or a girl? What are you?"
            } else if (result >= 24.9 && result < 29.9) {
                BMIResult = "Overweight"
                message = "Hello super sumo. Nice to meet you. Are you eating sandwich right now?"
            } else {
                BMIResult = "Obese"
                message = "You are not going to live longer... sorry :("
            }

        }
    }


}