/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ghalexandru.stackoverflow.api.StackOverflowApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val stackOverflowApi: StackOverflowApi,
    private val pagingConfig: PagingConfig,
) {

    fun fetchQuestionsLiveData(): Flow<PagingData<Question>> =
        Pager(pagingConfig, pagingSourceFactory = { QuestionPagingSource(stackOverflowApi) }).flow
}