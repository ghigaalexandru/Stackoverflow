/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.models

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("user_id")
    val id: Int,
    @SerializedName("profile_image")
    val image: String,
    @SerializedName("display_name")
    val name: String
)