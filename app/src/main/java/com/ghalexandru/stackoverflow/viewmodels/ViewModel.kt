/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ghalexandru.stackoverflow.models.Question
import com.ghalexandru.stackoverflow.network.Resource
import com.ghalexandru.stackoverflow.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getQuestions(): LiveData<Resource<List<Question>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = repository.getQuestions(1)
            emit(Resource.success(data = result.items))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Server communication error!"))
        }
    }
}