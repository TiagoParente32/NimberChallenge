package com.example.nimberchallenge.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nimberchallenge.data.local.dao.ReceiptDao
import com.example.nimberchallenge.data.local.entities.ReceiptEntity

@Database(entities = [ReceiptEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao
}