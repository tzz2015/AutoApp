package com.stardust.auojs.inrt.util

import android.annotation.SuppressLint
import java.text.ParsePosition
import java.text.SimpleDateFormat

class TimestampUtil {
    /**
     * Timestamp to String
     * @param Timestamp
     * @return String
     */
    @SuppressLint("SimpleDateFormat")
    fun transToString(time: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time)
    }

    /**
     * String to Timestamp
     * @param String
     * @return Timestamp
     */

    @SuppressLint("SimpleDateFormat")
    fun transToTimeStamp(date: String): Long {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date, ParsePosition(0)).time
    }
}