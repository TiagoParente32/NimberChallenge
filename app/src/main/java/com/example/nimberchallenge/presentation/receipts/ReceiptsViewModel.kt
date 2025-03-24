package com.example.nimberchallenge.presentation.receipts

import androidx.lifecycle.viewModelScope
import com.example.nimberchallenge.domain.models.Receipt
import com.example.nimberchallenge.domain.usecases.DeleteReceiptUseCase
import com.example.nimberchallenge.domain.usecases.GetReceiptsUseCase
import com.example.nimberchallenge.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReceiptsUiState(
    val receipts: List<Receipt> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class ReceiptsViewModel
    @Inject
    constructor(
        private val getReceiptsUseCase: GetReceiptsUseCase,
        private val deleteReceiptUseCase: DeleteReceiptUseCase,
    ) : BaseViewModel<ReceiptsUiState>() {
        init {
            fetchReceipts()
        }

        private fun fetchReceipts() {
            viewModelScope.launch {
                updateUiState {
                    copy(
                        isLoading = true,
                    )
                }
                try {
                    getReceiptsUseCase().collect { receiptsList ->
                        updateUiState {
                            copy(
                                receipts = receiptsList,
                                isLoading = false,
                            )
                        }
                    }
                } catch (e: Exception) {
                    updateUiState {
                        copy(
                            isLoading = false,
                            errorMessage = e.localizedMessage,
                        )
                    }
                }
            }
        }

        fun deleteReceipt(receipt: Receipt) {
            viewModelScope.launch {
                try {
                    deleteReceiptUseCase(receipt)
                    updateUiState {
                        copy(
                            receipts = uiState.value.receipts - receipt,
                        )
                    }
                } catch (e: Exception) {
                    updateUiState {
                        copy(
                            errorMessage = e.localizedMessage,
                        )
                    }
                }
            }
        }

        fun onAdd() {
            navigateTo("add_receipt")
        }

        override fun initialState() = ReceiptsUiState()
    }
