package com.sample.data.repository

import com.sample.data.source.local.ModelDao
import com.sample.data.source.remote.ApiResponse
import com.sample.data.source.remote.ApiService
import com.sample.domain.model.Model
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SampleRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var modelDao: ModelDao

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = RepositoryImpl(apiService, modelDao)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getModel returns success`() = runBlocking {
        val model = Model(1, "Sample Model")
        Mockito.`when`(apiService.getModel()).thenReturn(model)

        val response = repository.fetchData()

        assertEquals(ApiResponse.Success(model), response)
    }

    @Test
    fun `test getModel returns error`() = runBlocking {
        val exception = RuntimeException("Unexpected Error")
        Mockito.`when`(apiService.getModel()).thenThrow(exception)

        val response = repository.fetchData()

        assert(response is ApiResponse.Error && response.exception.message == "Unexpected Error")
    }
}