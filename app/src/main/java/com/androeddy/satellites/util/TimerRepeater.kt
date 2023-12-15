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
        if (isRunning) return
        runnable?.let {
            customHandler?.postDelayed(runnable!!, delayMs)
            isRunning = true
        }
    }

    fun stopTimer() {
        customHandler?.removeCallbacksAndMessages(null)
        isRunning = false
    }
}