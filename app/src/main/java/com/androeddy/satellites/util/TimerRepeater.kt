package com.androeddy.satellites.util

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.androeddy.satellites.ui.detail.SatelliteDetailFRVM

abstract class TimerRepeater(private val delayMs: Long) {
    @set:Synchronized
    var isRunning: Boolean = false
    private var customHandler: Handler? = null
    private var runnable: Runnable? = null

    protected abstract fun onTick()

    init {
        val handlerThread: HandlerThread = HandlerThread(TimerRepeater::class.java.simpleName)
        handlerThread.start()
        customHandler = Handler(handlerThread.looper)
        runnable = object : Runnable {
            override fun run() {
                onTick()
                customHandler?.postDelayed(this, delayMs)
            }

        }
    }

    fun startTimer() {
        Log.d("TIMER", "startTimer: Before isRunningCheck:$isRunning")
        if (isRunning) return
        Log.d("TIMER", "startTimer: After isRunningCheck:$isRunning")
        runnable?.let {
            customHandler?.postDelayed(runnable!!, delayMs)
            isRunning = true
        }
    }

    fun stopTimer() {
        Log.d("TIMER", "stopTimer: begin isRunning:$isRunning")
        customHandler?.removeCallbacksAndMessages(null)
        isRunning = false
        Log.d("TIMER", "stopTimer: end isRunning:$isRunning")
    }
}