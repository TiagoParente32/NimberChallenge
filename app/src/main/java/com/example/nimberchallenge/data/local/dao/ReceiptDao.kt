package com.example.nimberchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nimberchallenge.data.local.entities.ReceiptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: ReceiptEntity)

    @Query("SELECT * FROM receipts ORDER BY date DESC")
    fun getReceipts(): Flow<List<ReceiptEntity>>

    @Delete
    suspend fun deleteReceipt(receipt: ReceiptEntity)
}
