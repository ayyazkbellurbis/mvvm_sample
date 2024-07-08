package com.sample.data.repository

import com.sample.data.source.local.ModelDao
import com.sample.data.source.remote.ApiResponse
import com.sample.data.source.remote.ApiService
import com.sample.data.source.remote.safeApiCall
import com.sample.domain.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val modelDao: ModelDao
) : Repository {

    override suspend fun fetchData(): ApiResponse<Model> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response = apiService.getModel()
                modelDao.insertModel(response)
                response
            }
        }
    }
}