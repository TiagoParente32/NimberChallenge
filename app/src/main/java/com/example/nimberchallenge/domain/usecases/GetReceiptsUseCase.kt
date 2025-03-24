package com.example.nimberchallenge.domain.usecases

import com.example.nimberchallenge.data.repository.ReceiptRepository
import com.example.nimberchallenge.domain.models.Receipt
import kotlinx.coroutines.flow.Flow

class GetReceiptsUseCase(
    private val repository: ReceiptRepository,
) {
    operator fun invoke(): Flow<List<Receipt>> = repository.getReceipts()
}
