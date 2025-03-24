package com.example.nimberchallenge.domain.usecases

import com.example.nimberchallenge.data.repository.ReceiptRepository
import com.example.nimberchallenge.domain.models.Receipt

class AddReceiptUseCase(
    private val repository: ReceiptRepository,
) {
    suspend operator fun invoke(receipt: Receipt) = repository.addReceipt(receipt)
}
