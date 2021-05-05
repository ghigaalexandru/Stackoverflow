/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.repositories

import com.ghalexandru.stackoverflow.models.Question
import com.ghalexandru.stackoverflow.models.StackOverflowResponse
import com.ghalexandru.stackoverflow.network.Resource
import com.ghalexandru.stackoverflow.network.StackOverflowApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(private val stackOverflowApi: StackOverflowApi) {

    suspend fun fetchQuestions(page: Int): Flow<Resource<StackOverflowResponse<Question>>> {
        return flow {
            emit(Resource.loading(data = null))
            try {
                val result = stackOverflowApi.getQuestions(page)
                emit(Resource.success(data = result))
            } catch (e: Exception) {
                emit(
                    Resource.error(
                        data = null,
                        message = e.message ?: "Server communication error!"
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}