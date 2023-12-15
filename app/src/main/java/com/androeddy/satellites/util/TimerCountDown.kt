package com.androeddy.satellites.util

import android.os.Handler
import android.os.HandlerThread
import android.os.Message

abstract class TimerCountDown<T : Any>(private val delayMs: Long) {
    private val messageCode = 1923
    private val handlerThread = HandlerThread(TimerCountDown::class.java.simpleName)
    private var triggerMessage: T? = null
    private var handler: Handler? = null

    protected abstract fun onTrigger(triggerMessage: T?)

    init {
        handlerThread.start()
        handler = object : Handler(handlerThread.looper) {
            override fun handleMessage(msg: Message) {
                if (msg.what == messageCode) {
                    onTrigger(triggerMessage)
                }
            }
        }
    }

    fun restart(triggerMessage: T?) {
        this.triggerMessage = triggerMessage
        handler?.removeMessages(messageCode);
        handler?.sendEmptyMessageDelayed(messageCode, delayMs);
    }

    fun stop() {
        handler?.removeCallbacksAndMessages(null)
    }
}