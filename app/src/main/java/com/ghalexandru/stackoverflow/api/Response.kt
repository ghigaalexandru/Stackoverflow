/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.api

import com.google.gson.annotations.SerializedName

data class Response<out T>(
    @SerializedName("items") val items: List<T>,
    @SerializedName("has_more") val hasMore: Boolean,
    @SerializedName("error_message") val errorMessage: String? = null,
    @SerializedName("backoff") val backoff: Int? = null
)