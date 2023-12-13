package com.androeddy.satellites.util


object Extensions {
    fun Boolean?.orFalse(): Boolean {
        return this ?: false
    }
}
