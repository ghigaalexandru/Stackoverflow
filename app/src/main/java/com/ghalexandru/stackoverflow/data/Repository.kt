/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ghalexandru.stackoverflow.api.StackOverflowApi
import javax.inject.Inject

class Repository @Inject constructor(
    private val questionPagingSource: QuestionPagingSource,
    private val pagingConfig: PagingConfig,
) {

    fun fetchQuestionsLiveData() =
        Pager(pagingConfig, pagingSourceFactory = { questionPagingSource }).liveData
}