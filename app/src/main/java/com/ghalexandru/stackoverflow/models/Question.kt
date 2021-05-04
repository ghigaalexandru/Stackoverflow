/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.models

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("owner")
    val onwer: Owner,
    @SerializedName("is_answered")
    val answered: Boolean,
    @SerializedName("view_count")
    val views: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("question_id")
    val id: Int,
    @SerializedName("up_vote_count")
    val upVoteCount: Int,
    @SerializedName("down_vote_count")
    val downVoteCount: Int,
    @SerializedName("creation_date")
    val creationDate: Long
)