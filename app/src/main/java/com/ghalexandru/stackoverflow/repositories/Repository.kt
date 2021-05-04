/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.repositories

import com.ghalexandru.stackoverflow.network.QuestionsApi
import javax.inject.Inject

class Repository @Inject constructor(private val questionsApi: QuestionsApi) {
    suspend fun getQuestions(page: Int) = questionsApi.getQuestions(page)
}