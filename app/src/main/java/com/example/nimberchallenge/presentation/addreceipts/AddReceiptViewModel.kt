package com.example.nimberchallenge.presentation.addreceipts

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import com.example.nimberchallenge.domain.models.Receipt
import com.example.nimberchallenge.domain.usecases.AddReceiptUseCase
import com.example.nimberchallenge.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

data class AddReceiptUiState(
    val imageUri: Uri? = null,
    val amount: String = "",
    val currency: String = "",
    val tempUri: Uri? = null,
    val toastMessage: String? = null,
)

@HiltViewModel
class AddReceiptViewModel
    @Inject
    constructor(
        private val addReceiptUseCase: AddReceiptUseCase,
    ) : BaseViewModel<AddReceiptUiState>() {
        override fun initialState() = AddReceiptUiState()

        fun addReceipt() {
            with(uiState.value) {
                if (imageUri == null || amount.isBlank() || currency.isBlank()) {
                    updateUiState {
                        copy(
                            toastMessage = "Please fill all fields",
                        )
                    }
                    return
                }

                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                val receipt =
                    Receipt(
                        id = 0,
                        photoPath = imageUri.toString(),
                        date = currentDate,
                        amount = amount.toDouble(),
                        currency = currency,
                    )
                viewModelScope.launch {
                    addReceiptUseCase(receipt)
                    popBackStack()
                }
            }
        }

        fun onTakePhoto(context: Context) {
            val file = File(context.filesDir, "${UUID.randomUUID()}.jpg")
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            updateUiState {
                copy(
                    tempUri = uri,
                )
            }
        }

        fun saveImageUri() {
            updateUiState {
                copy(
                    imageUri = tempUri,
                )
            }
        }

        fun onBackPressed() {
            popBackStack()
        }

        fun onAmountUpdated(text: String) {
            if (text.none { it.isDigit() }) return
            updateUiState {
                copy(
                    amount = text,
                )
            }
        }

        fun onCurrencyUpdated(text: String) {
            updateUiState {
                copy(
                    currency = text,
                )
            }
        }

        fun resetToastMessage() {
            updateUiState {
                copy(
                    toastMessage = null,
                )
            }
        }
    }
