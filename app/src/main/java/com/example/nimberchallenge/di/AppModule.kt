package com.example.nimberchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.nimberchallenge.data.AppDatabase
import com.example.nimberchallenge.data.local.dao.ReceiptDao
import com.example.nimberchallenge.data.repository.ReceiptRepository
import com.example.nimberchallenge.data.repository.ReceiptRepositoryImpl
import com.example.nimberchallenge.domain.usecases.AddReceiptUseCase
import com.example.nimberchallenge.domain.usecases.DeleteReceiptUseCase
import com.example.nimberchallenge.domain.usecases.GetReceiptsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "expense_db").build()

    @Provides
    fun provideReceiptDao(db: AppDatabase) = db.receiptDao()

    @Provides
    fun provideReceiptRepository(dao: ReceiptDao): ReceiptRepository = ReceiptRepositoryImpl(dao)

    @Provides
    fun provideAddReceiptUseCase(repository: ReceiptRepository) = AddReceiptUseCase(repository)

    @Provides
    fun provideGetReceiptsUseCase(repository: ReceiptRepository) = GetReceiptsUseCase(repository)

    @Provides
    fun provideDeleteReceiptUseCase(repository: ReceiptRepository) = DeleteReceiptUseCase(repository)
}
