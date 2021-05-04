/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.util

import android.util.Log
import com.ghalexandru.stackoverflow.BuildConfig
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Any.logd(message: String) {
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}

fun Any.loge(message: String, throwable: Throwable? = null) {
    Log.e(this::class.java.simpleName, message, throwable)
}

fun Long.elapsedTime(): String {
    val dateTime = Instant.ofEpochSecond(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val duration = Duration.between(dateTime, LocalDateTime.now())

    return when {
        duration.seconds < 60 -> {
            "${duration.seconds} sec. ago"
        }
        duration.toMinutes() < 60 -> {
            "${duration.toMinutes()} min. ago"
        }
        duration.toHours() < 60 -> {
            "${duration.toHours()} hours. ago"
        }
        else -> "${duration.toDays()} days. ago"
    }
}