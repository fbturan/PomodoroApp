package com.my.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var header: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var progressBar: ProgressBar

    private var isTimerRunning = false
    private var workDuration: Long = 25 * 60 * 1000 // 25
    private var currentDuration: Long = workDuration

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        header = findViewById(R.id.header)
        startButton = findViewById(R.id.startButton)
        resetButton = findViewById(R.id.resetButton)
        progressBar = findViewById(R.id.progressBar)

        supportActionBar?.title = header.text.toString()
        progressBar.visibility = ProgressBar.INVISIBLE

        startButton.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(currentDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentDuration = millisUntilFinished

                updateTimerText()
                updateProgressBar()
                updateActionBarTitle()
            }

            override fun onFinish() {
                resetTimer()
            }
        }.start()

        isTimerRunning = true
        startButton.text = "Pause"
        progressBar.visibility = ProgressBar.VISIBLE
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        isTimerRunning = false
        startButton.text = "Resume"
        progressBar.visibility = ProgressBar.INVISIBLE
    }

    private fun resetTimer() {
        countDownTimer.cancel()
        isTimerRunning = false
        startButton.text = "Start"
        currentDuration = workDuration
        updateTimerText()
        updateProgressBar()
        updateActionBarTitle()
        progressBar.visibility = ProgressBar.INVISIBLE
    }

    private fun updateTimerText() {
        val minutes = (currentDuration / 1000) / 60
        val seconds = (currentDuration / 1000) % 60
        header.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateProgressBar() {
        val progress = ((workDuration - currentDuration).toFloat() / workDuration.toFloat() * 100).toInt()
        progressBar.progress = progress
    }

    private fun updateActionBarTitle() {
        supportActionBar?.title = header.text.toString()
    }
}
