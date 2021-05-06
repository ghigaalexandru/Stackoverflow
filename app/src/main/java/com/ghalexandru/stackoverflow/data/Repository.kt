/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ghalexandru.stackoverflow.api.StackOverflowApi
import javax.inject.Inject

class Repository @Inject constructor(private val stackOverflowApi: StackOverflowApi) {

    fun fetchQuestionsLiveData() =
        Pager(config = PagingConfig(pageSize = 30, maxSize = 90, enablePlaceholders = false),
            pagingSourceFactory = { QuestionPagingSource(stackOverflowApi) }).liveData
}