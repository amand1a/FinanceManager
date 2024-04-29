package com.example.financemanager.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setNameCurrency(value: String)

    suspend fun getNameCurrency(): Flow<String>
}