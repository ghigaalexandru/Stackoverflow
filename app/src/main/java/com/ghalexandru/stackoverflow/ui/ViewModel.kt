/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ghalexandru.stackoverflow.data.Question
import com.ghalexandru.stackoverflow.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(application: Application, private val repository: Repository) :
    AndroidViewModel(application) {

    fun fetchQuestions(): LiveData<PagingData<Question>> =
        repository.fetchQuestionsLiveData().cachedIn(viewModelScope)
}