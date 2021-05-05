/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.network

import com.ghalexandru.stackoverflow.models.Question
import com.ghalexandru.stackoverflow.models.StackOverflowResponse
import com.ghalexandru.stackoverflow.util.Constants.PAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowApi {

    @GET("questions?order=desc&sort=creation&site=stackoverflow")
    suspend fun getQuestions(@Query(PAGE) page: Int): StackOverflowResponse<Question>
}