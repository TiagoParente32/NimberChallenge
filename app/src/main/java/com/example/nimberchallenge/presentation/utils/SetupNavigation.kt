package com.example.nimberchallenge.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.nimberchallenge.presentation.BaseViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SetupNavigation(
    viewModel: BaseViewModel<*>,
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collectLatest { event ->
            when (event) {
                "pop_back_stack" -> navController.popBackStack()
                else -> navController.navigate(event)
            }
        }
    }
}
