/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ghalexandru.stackoverflow.data.Question
import com.ghalexandru.stackoverflow.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(repository: Repository) :
    ViewModel() {

    val fetchQuestions: Flow<PagingData<Question>> =
        repository.fetchQuestionsLiveData().cachedIn(viewModelScope).distinctUntilChanged()
}