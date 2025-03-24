package com.example.nimberchallenge.presentation.addreceipts

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nimberchallenge.presentation.utils.RequestCameraPermission
import com.example.nimberchallenge.presentation.utils.SetupNavigation
import com.example.nimberchallenge.presentation.utils.constants.PaddingConstants.mediumPadding
import com.example.nimberchallenge.presentation.utils.constants.ReceiptsConstants.imagePreviewSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReceiptScreen(
    viewModel: AddReceiptViewModel = hiltViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    RequestCameraPermission(
        onPermissionDenied = {
            viewModel.onBackPressed()
        },
    )
    SetupNavigation(viewModel, navController)
    LaunchedEffect(uiState.value.toastMessage) {
        uiState.value.toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetToastMessage()
        }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                viewModel.saveImageUri()
            }
        }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Receipt") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBackPressed() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RoundedButton(text = "Take Photo") {
                viewModel.onTakePhoto(context)
                viewModel.uiState.value.tempUri?.let {
                    launcher.launch(it)
                }
            }

            uiState.value.imageUri?.let {
                Image(
                    modifier =
                        Modifier
                            .size(
                                imagePreviewSize,
                            ).clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Captured Photo",
                )
            }

            RoundedTextField(
                value = uiState.value.amount,
                onValueChange = viewModel::onAmountUpdated,
                label = "Amount",
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                onAction = { focusManager.moveFocus(FocusDirection.Down) },
            )

            RoundedTextField(
                value = uiState.value.currency,
                onValueChange = viewModel::onCurrencyUpdated,
                label = "Currency",
                imeAction = ImeAction.Done,
                onAction = { focusManager.clearFocus() },
            )
            Spacer(modifier = Modifier.weight(1f))

            RoundedButton(text = "Save Receipt", modifier = Modifier.fillMaxWidth()) {
                viewModel.addReceipt()
            }
        }
    }
}

@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        modifier = modifier.padding(mediumPadding),
    ) {
        Text(text)
    }
}

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction,
    onAction: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions =
            KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
        keyboardActions =
            KeyboardActions(
                onDone = { onAction() },
                onNext = { onAction() },
            ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(mediumPadding),
    )
}
