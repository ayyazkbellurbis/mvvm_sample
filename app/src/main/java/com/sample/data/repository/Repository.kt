package com.sample.data.repository

import com.sample.data.source.remote.ApiResponse
import com.sample.domain.model.Model

interface Repository {
    suspend fun fetchData(): ApiResponse<Model>
}