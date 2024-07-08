package com.sample.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sample.navigation.NavGraph
import com.sample.ui.theme.SampleTheme

@Composable
fun SampleApp() {
    SampleTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            NavGraph(navController)
        }
    }
}