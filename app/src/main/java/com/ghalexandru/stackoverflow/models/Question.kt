/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.models

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("owner")
    val onwer: Owner? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("question_id")
    val id: Int,
)