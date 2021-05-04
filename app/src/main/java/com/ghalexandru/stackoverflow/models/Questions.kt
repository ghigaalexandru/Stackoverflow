/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.models

import com.google.gson.annotations.SerializedName

data class Questions(@SerializedName("items") val questions: List<Question>)