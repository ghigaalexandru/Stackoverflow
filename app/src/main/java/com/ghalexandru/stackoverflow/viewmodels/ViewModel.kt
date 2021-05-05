/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghalexandru.stackoverflow.models.Question
import com.ghalexandru.stackoverflow.models.StackOverflowResponse
import com.ghalexandru.stackoverflow.network.Resource
import com.ghalexandru.stackoverflow.network.Status
import com.ghalexandru.stackoverflow.repositories.Repository
import com.ghalexandru.stackoverflow.util.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var page = 0
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
        viewModelScope.launch {
            repository.fetchQuestions(page).collect {
                if (it.status == Status.ERROR) {
                    this@ViewModel.page -= 1
                }
                questions.value = it
            }
        }
    }
}