package com.example.nimberchallenge.data.repository

import com.example.nimberchallenge.data.local.dao.ReceiptDao
import com.example.nimberchallenge.data.local.mappers.toDomain
import com.example.nimberchallenge.data.local.mappers.toEntity
import com.example.nimberchallenge.domain.models.Receipt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReceiptRepositoryImpl(
    private val dao: ReceiptDao,
) : ReceiptRepository {
    override suspend fun addReceipt(receipt: Receipt) {
        dao.insertReceipt(receipt.toEntity())
    }

    override fun getReceipts(): Flow<List<Receipt>> = dao.getReceipts().map { entities -> entities.map { it.toDomain() } }

    override suspend fun deleteReceipt(receipt: Receipt) {
        dao.deleteReceipt(receipt.toEntity())
    }
}
