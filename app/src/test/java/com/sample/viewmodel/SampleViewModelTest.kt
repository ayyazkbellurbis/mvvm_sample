package com.sample.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sample.CoroutineRule
import com.sample.data.repository.Repository
import com.sample.data.repository.RepositoryImpl
import com.sample.data.source.remote.ApiResponse
import com.sample.domain.model.Model
import com.sample.presentation.viewmodel.SampleViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SampleViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var viewModel: SampleViewModel
    private val repository: Repository = mock(RepositoryImpl::class.java)

    @Before
    fun setup() {
        viewModel = SampleViewModel(repository)
    }

    @Test
    fun `fetchData updates StateFlow with repository result`() = testScope.runBlockingTest {
        // Arrange
        val expectedData = ApiResponse.Success(Model(1, "Hello, World!"))
        whenever(repository.fetchData()).thenReturn(expectedData)

        // Act
        viewModel.fetchModel()

        // Assert
        val stateFlowValue = viewModel.model.first()
        assert(stateFlowValue == expectedData)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }
}