package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val restTime: Long = 10000
    private val exerciseTime: Long = 30000

    private var tts: TextToSpeech? = null
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()
        tts = TextToSpeech(this, this)

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }

    private fun setRestProgressBar() {
        restTimer = object : CountDownTimer(restTime, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList!!.size){
                    setupExerciseView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Exercise finished",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun setExerciseProgressBar() {
        exerciseTimer = object : CountDownTimer(exerciseTime, 1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                setupRestView()
            }
        }.start()
    }

    private fun setupRestView() {
        currentExercisePosition++
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.flProgressBarExercise?.visibility = View.INVISIBLE
        binding?.tvGetReady?.text = "GET READY FOR"
        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvUCExercise?.visibility = View.VISIBLE
        binding?.tvUpcomingExercise?.visibility = View.VISIBLE

        if(currentExercisePosition < exerciseList!!.size) {
            binding?.tvUpcomingExercise?.text = exerciseList!![currentExercisePosition].getName()
        } else {
            binding?.tvUpcomingExercise?.text = "There is no more exercise"
        }

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    private fun setupExerciseView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.flProgressBarExercise?.visibility = View.VISIBLE
        binding?.tvGetReady?.text = exerciseList!![currentExercisePosition].getName()
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUCExercise?.visibility = View.INVISIBLE
        binding?.tvUpcomingExercise?.visibility = View.INVISIBLE

        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Missing data or language is not supported")
            }
        } else {
            Log.e("TTS", "There is some error")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        binding = null
    }
}