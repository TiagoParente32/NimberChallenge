package com.example.nimberchallenge.data.repository

import com.example.nimberchallenge.domain.models.Receipt
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {
    suspend fun addReceipt(receipt: Receipt)

    fun getReceipts(): Flow<List<Receipt>>

    suspend fun deleteReceipt(receipt: Receipt)
}
