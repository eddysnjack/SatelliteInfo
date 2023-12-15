package com.androeddy.satellites.util

import java.text.NumberFormat
import java.util.Locale




object StringHelper {
    fun formatNumberWithSeparator(number: Long): String? {
        val locale = Locale.getDefault()
        val numberFormat: NumberFormat = NumberFormat.getInstance(locale)
        numberFormat.isGroupingUsed = true
        return numberFormat.format(number)
    }
}