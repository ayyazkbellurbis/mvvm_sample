package com.sample.data.source.remote

import com.sample.domain.model.Model
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("endpoint")
    suspend fun getModel(): Model
}