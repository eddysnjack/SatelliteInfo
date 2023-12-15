package com.androeddy.satellites.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    const val JSON_DATE_FORMAT = "yyyy-MM-dd"
    fun convertStringDateToLocaleFormat(dateStr: String, format: String, locale: Locale = Locale.getDefault()): String {
        var result = ""
        val sdf = SimpleDateFormat(format, locale)
        var date: Date? = null
        try {
            date = sdf.parse(dateStr)
        } catch (e: Exception) {
        }

        if (date != null) {
            val dateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM, locale);
            result = dateFormat.format(date)
        }

        return result
    }
}