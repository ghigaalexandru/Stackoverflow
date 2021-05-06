/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ghalexandru.stackoverflow.R
import com.ghalexandru.stackoverflow.models.Question
import com.ghalexandru.stackoverflow.models.StackOverflowResponse
import com.ghalexandru.stackoverflow.network.Resource
import com.ghalexandru.stackoverflow.network.Status
import com.ghalexandru.stackoverflow.repositories.Repository
import com.ghalexandru.stackoverflow.ui.BaseApplication
import com.ghalexandru.stackoverflow.util.Constants.DELAY_API_CALL
import com.ghalexandru.stackoverflow.util.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(application: Application, private val repository: Repository) :
    AndroidViewModel(application) {

    private var page = 0
    val questions = MutableLiveData<Resource<StackOverflowResponse<Question>>>()

    fun fetchFirstQuestions() {
        page = 1
        fetchQuestions(page)
    }

    fun fetchNextQuestions() {
        questions.value?.let { if (it.status == Status.LOADING || it.data?.hasMore == false) return }
        page += 1
        fetchQuestions(page)
    }

    private fun fetchQuestions(page: Int) {
        logd("Page = $page")
        viewModelScope.launch(Dispatchers.IO) {
            questions.postValue(Resource.loading(data = null))
            //  delay the api calls in order to prevent more than 30 calls/second
            delay(DELAY_API_CALL)

            val result = try {
                repository.fetchQuestions(page)
            } catch (e: Exception) {
                fetchQuestionsError(e.message)
                return@launch
            }

            //  if there is a backoff request, then delay this coroutine
            result.backoff?.let { backoff -> delay(backoff * 1000L) }
            if (result.errorMessage == null) {
                questions.postValue(Resource.success(data = result))
            } else {
                fetchQuestionsError(result.errorMessage)
            }
        }
    }

    private fun fetchQuestionsError(errorMessage: String?) {
        /* the page will be decremented in order to give
         to the user an option to reload the same page */
        this@ViewModel.page -= 1
        questions.postValue(
            Resource.error(
                data = null,
                message = errorMessage
                    ?: getApplication<BaseApplication>().getString(R.string.server_comunication_error)
            )
        )
    }
}