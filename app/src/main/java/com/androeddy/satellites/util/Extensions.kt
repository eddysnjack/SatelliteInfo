package com.androeddy.satellites.util

import android.view.View


object Extensions {
    fun Boolean?.orFalse(): Boolean {
        return this ?: false
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }
}
