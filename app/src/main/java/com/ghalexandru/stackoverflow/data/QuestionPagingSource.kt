/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ghalexandru.stackoverflow.api.StackOverflowApi
import com.ghalexandru.stackoverflow.util.Constants.FIRST_PAGE
import com.ghalexandru.stackoverflow.util.logd
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuestionPagingSource @Inject constructor(private val stackOverflowApi: StackOverflowApi) :
    PagingSource<Int, Question>() {
    override fun getRefreshKey(state: PagingState<Int, Question>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Question> {
        val page = params.key ?: FIRST_PAGE

        return try {
            logd("Page is $page")
            val response = stackOverflowApi.getQuestions(page, params.loadSize)
            val questions = response.items

            LoadResult.Page(
                data = questions,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (!response.hasMore || questions.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}