/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.network

import com.ghalexandru.stackoverflow.models.Questions
import com.ghalexandru.stackoverflow.util.Constants.PAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsApi {

    @GET("questions?order=desc&sort=creation&site=stackoverflow")
    suspend fun getQuestions(@Query(PAGE) page: Int): Questions
}