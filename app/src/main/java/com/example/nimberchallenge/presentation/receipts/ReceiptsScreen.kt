package com.example.nimberchallenge.presentation.receipts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nimberchallenge.domain.models.Receipt
import com.example.nimberchallenge.presentation.utils.SetupNavigation
import com.example.nimberchallenge.presentation.utils.constants.PaddingConstants.mediumPadding
import com.example.nimberchallenge.presentation.utils.constants.ReceiptsConstants.imageSize

@Composable
fun ReceiptsScreen(
    viewModel: ReceiptsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    SetupNavigation(viewModel, navController)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::onAdd,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Receipt")
            }
        },
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                LoadingScreen()
            }
            uiState.receipts.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding).padding(mediumPadding),
                    verticalArrangement = Arrangement.spacedBy(mediumPadding),
                ) {
                    items(uiState.receipts) { receipt ->
                        ReceiptItem(
                            receipt = receipt,
                            onDelete = viewModel::deleteReceipt,
                        )
                    }
                }
            }
            else -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(mediumPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "No Receipts saved! Please add some by clicking the Add button",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier =
            Modifier
                .fillMaxSize() // This will make the Box take up the entire screen
                .padding(mediumPadding), // Optional padding around the box
    ) {
        CircularProgressIndicator(
            modifier =
                Modifier
                    .align(Alignment.Center) // Centers the spinner inside the Box
                    .size(50.dp), // Optional: size of the spinner
        )
    }
}

@Composable
fun ReceiptItem(
    receipt: Receipt,
    onDelete: (Receipt) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    // does nothing for now
                },
    ) {
        Row(modifier = Modifier.padding(mediumPadding)) {
            Image(
                painter = rememberAsyncImagePainter(receipt.photoPath),
                contentDescription = "Receipt",
                modifier = Modifier.size(imageSize),
            )
            Column(modifier = Modifier.padding(start = mediumPadding)) {
                Text("Date: ${receipt.date}", style = MaterialTheme.typography.bodySmall)
                Text("Amount: ${receipt.amount} ${receipt.currency}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {
                onDelete(receipt)
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
