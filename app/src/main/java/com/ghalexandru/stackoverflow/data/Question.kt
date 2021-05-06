/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.data

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("owner")
    val owner: Owner? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("question_id")
    val id: Int
)