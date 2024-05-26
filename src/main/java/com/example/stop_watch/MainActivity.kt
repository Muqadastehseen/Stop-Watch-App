package com.example.stop_watch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.stop_watch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //step 2

    private var isRunning = false
    private var timerSeconds = 0

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            timerSeconds++
            val hours = timerSeconds / 3600
            val minutes = (timerSeconds % 3600) / 60
            val seconds = timerSeconds % 60

            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.timerText.text = time
            handler.postDelayed(this, 1000)


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Step 6

        binding.startBtn.setOnClickListener {
            startTimer()
        }

        binding.stopBtn.setOnClickListener {
            stopTimer()
        }

        binding.resetBtn.setOnClickListener {
            resetTimer()
        }


    }
    // Step 3

    private fun startTimer() {
        if (!isRunning) {
            handler.postDelayed(runnable, 1000)
            isRunning = true

            binding.startBtn.isEnabled = false
            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true
        }

    }

    //step 4
    private fun stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false

            binding.startBtn.isEnabled = true
            binding.startBtn.text = "Resume"
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled=true
        }

    }

    //step 5

    private fun resetTimer() {
       stopTimer()

        timerSeconds=0
        binding.timerText.text="00:00:00"

        binding.startBtn.isEnabled=true
        binding.resetBtn.isEnabled=false
        binding.startBtn.text="Start"

        }
    }