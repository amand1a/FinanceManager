package com.example.financemanager.di.modules

import android.content.Context
import com.example.financemanager.data.dataBase.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
    @Provides
    fun getDb(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase(context) as AppDatabase
    }

    @Provides
    fun getExpensesDao(dataBase: AppDatabase) = dataBase.expensesDao()
}