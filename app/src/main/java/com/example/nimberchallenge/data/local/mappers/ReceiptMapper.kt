package com.example.nimberchallenge.data.local.mappers

import com.example.nimberchallenge.data.local.entities.ReceiptEntity
import com.example.nimberchallenge.domain.models.Receipt

fun ReceiptEntity.toDomain(): Receipt = Receipt(id, photoPath, date, amount, currency)

fun Receipt.toEntity(): ReceiptEntity = ReceiptEntity(id, photoPath, date, amount, currency)
