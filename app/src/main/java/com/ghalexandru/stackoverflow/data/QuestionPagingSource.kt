/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ghalexandru.stackoverflow.api.StackOverflowApi
import com.ghalexandru.stackoverflow.util.Constants.FIRST_PAGE
import retrofit2.HttpException
import java.io.IOException

class QuestionPagingSource(private val stackOverflowApi: StackOverflowApi) :
    PagingSource<Int, Question>() {
    override fun getRefreshKey(state: PagingState<Int, Question>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Question> {
        val position = params.key ?: FIRST_PAGE

        return try {
            val response = stackOverflowApi.getQuestions(position)
            val questions = response.items

            LoadResult.Page(
                data = questions,
                prevKey = if (position == FIRST_PAGE) null else position - 1,
                nextKey = if (!response.hasMore) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}