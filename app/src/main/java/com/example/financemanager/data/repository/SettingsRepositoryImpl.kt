package com.example.financemanager.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.financemanager.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    override suspend fun setNameCurrency(value: String) {
        dataStore.edit {
            it[currencyNameString] = value
        }
    }

    override suspend fun getNameCurrency(): Flow<String> {
        return dataStore.data.map {
            it[currencyNameString] ?: ""
        }
    }

   private companion object {
        val currencyNameString = stringPreferencesKey("currencyName")
   }
}