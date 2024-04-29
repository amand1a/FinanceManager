package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanager.domain.repository.SettingsRepository
import com.example.financemanager.presentation.model.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState(""))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getNameCurrency().collect{
                _uiState.update { state ->
                    state.copy(currencyName = it)
                }
            }
        }
    }

    fun setCurrencyName(value: String){
        _uiState.update {
            _uiState.value.copy(currencyName = value)
        }
    }

    fun setCurrencyInDataStore(){
        viewModelScope.launch {
            settingsRepository.setNameCurrency(_uiState.value.currencyName)
        }
    }
}