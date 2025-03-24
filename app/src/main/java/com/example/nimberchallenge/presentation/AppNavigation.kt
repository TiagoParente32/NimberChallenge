package com.example.nimberchallenge.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nimberchallenge.presentation.addreceipts.AddReceiptScreen
import com.example.nimberchallenge.presentation.receipts.ReceiptsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "receipts") {
        composable("receipts") { ReceiptsScreen(navController = navController) }
        composable("add_receipt") { AddReceiptScreen(navController = navController) }
    }
}
