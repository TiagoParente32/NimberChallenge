package com.example.nimberchallenge.domain.models

data class Receipt(
    val id: Int,
    val photoPath: String,
    val date: String,
    val amount: Double,
    val currency: String,
)
