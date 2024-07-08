package com.sample.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sample.data.source.remote.ApiResponse
import com.sample.navigation.NavigationRoutes
import com.sample.presentation.viewmodel.SampleViewModel

@Composable
fun SampleScreen(navController: NavHostController, viewModel: SampleViewModel = hiltViewModel()) {
    val modelState by viewModel.model.collectAsState()
    Column {
        when (val state = modelState) {
            is ApiResponse.Loading -> {
                CircularProgressIndicator()
            }

            is ApiResponse.Success -> {
                Text(text = "Model: ${state.data.name}")
            }

            is ApiResponse.Error -> {
                Text(text = "Error: ${state.exception.message}")
            }
        }

        Button(onClick = { navController.navigate(NavigationRoutes.NEXT_SCREEN) }) {
            Text(text = "Go to NextScreen")
        }
    }
}