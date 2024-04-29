package com.example.financemanager.di.modules

import com.example.financemanager.data.repository.ExpensesRepositoryImpl
import com.example.financemanager.data.repository.SettingsRepositoryImpl
import com.example.financemanager.domain.repository.ExpensesRepository
import com.example.financemanager.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun getExpensesRepository(impl: ExpensesRepositoryImpl): ExpensesRepository {
        return impl
    }

    @Provides
    fun getSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository{
        return impl
    }
}