/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.api

import com.ghalexandru.stackoverflow.data.Question
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowApi {

    @GET("questions?order=desc&sort=creation&site=stackoverflow")
    suspend fun getQuestions(
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int,
    ): Response<Question>
}