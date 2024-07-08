package com.sample.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.data.repository.Repository
import com.sample.data.source.remote.ApiResponse
import com.sample.domain.model.Model
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _model = MutableStateFlow<ApiResponse<Model>>(ApiResponse.Loading)
    val model: StateFlow<ApiResponse<Model>> = _model

    init {
        fetchModel()
    }

    fun fetchModel() {
        viewModelScope.launch {
            _model.value = ApiResponse.Loading
            val response = repository.fetchData()
            _model.value = response
        }
    }
}