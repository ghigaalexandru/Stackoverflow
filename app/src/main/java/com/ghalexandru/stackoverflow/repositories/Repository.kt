/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.repositories

import com.ghalexandru.stackoverflow.network.StackOverflowApi
import javax.inject.Inject

class Repository @Inject constructor(private val stackOverflowApi: StackOverflowApi) {

    suspend fun fetchQuestions(page: Int) = stackOverflowApi.getQuestions(page)
}