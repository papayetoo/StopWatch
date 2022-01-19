package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var isRunning: Boolean = false
    private var timerTask: Timer? = null
    private var lap: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabButton.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }

        labButton.setOnClickListener {
            addLab()
        }

        refreshButton.setOnClickListener {
            reset()
        }
    }

    private fun start() {
        fabButton.setImageResource(R.drawable.ic_baseline_pause_24)

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            var milli = time % 100
            runOnUiThread {
                secondsTextView.text = "$sec"
                smallSecondsTextView.text = "$milli"
            }
        }
    }

    private fun pause() {
        fabButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()
    }

    private fun addLab() {
        var textView = TextView(this)
        var lapTime = this.time
        textView.text = "$lap LAB: ${lapTime / 100}.${lapTime % 100}"

        labLayout.addView(textView, 0)
        this.lap++
    }

    private fun reset() {
        this.time = 0
        this.isRunning = false
        fabButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        secondsTextView.text = "0"
        smallSecondsTextView.text = "00"

        labLayout.removeAllViews()
        this.lap = 1
    }
}