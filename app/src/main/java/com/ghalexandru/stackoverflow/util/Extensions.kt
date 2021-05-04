/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.util

import android.util.Log
import com.ghalexandru.stackoverflow.BuildConfig

fun Any.logd(message: String) {
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}

fun Any.loge(message: String, throwable: Throwable? = null) {
    Log.e(this::class.java.simpleName, message, throwable)
}