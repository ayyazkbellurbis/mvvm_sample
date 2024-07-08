package com.sample

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.sample.data.repository.Repository
import com.sample.data.source.remote.ApiResponse
import com.sample.domain.model.Model
import com.sample.presentation.screen.SampleScreen
import com.sample.presentation.viewmodel.SampleViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.whenever
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@MediumTest
class SampleScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var repository: Repository

    private lateinit var viewModel: SampleViewModel

    @Before
    fun setup() {
        // Set up Hilt
        // You need to provide Hilt test application and other necessary setup

        // Mock Repository
        viewModel = SampleViewModel(repository)
    }

    @Test
    fun testDisplaySuccess() = runBlocking {
        // Arrange
        val expectedData = ApiResponse.Success(Model(1, "Hello, World!"))
        whenever(repository.fetchData()).thenReturn(expectedData)

        // Act
        viewModel.fetchModel()
        composeTestRule.setContent {
            SampleScreen(rememberNavController(), viewModel = viewModel)
        }

        // Assert
        composeTestRule.onNodeWithText("Data: Hello, World!").assertIsDisplayed()
    }

    @Test
    suspend fun testDisplayLoading() {
        // Arrange
        val loadingState = ApiResponse.Loading
        whenever(repository.fetchData()).thenReturn(loadingState)

        // Act
        viewModel.fetchModel()
        composeTestRule.setContent {
            SampleScreen(rememberNavController() , viewModel = viewModel)
        }

        // Assert
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }
}