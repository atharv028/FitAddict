package com.tare.fitaddict.utils

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun setDate(): String
    {
        val date: String
        val curr = Date()
        val formatter = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
        date = formatter.format(curr)
        return date
    }
}