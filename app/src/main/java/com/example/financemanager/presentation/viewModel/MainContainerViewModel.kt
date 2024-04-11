package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.financemanager.presentation.model.ContainerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainContainerViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        ContainerUiState(
            selectedMonth = LocalDateTime.now()
        )
    )
    val uiState = _uiState.asStateFlow()

    fun getNextMoth() {
        _uiState.value = ContainerUiState(_uiState.value.selectedMonth.plusMonths(1))
    }

    fun getPrevMoth() {
        _uiState.value = ContainerUiState(_uiState.value.selectedMonth.minusMonths(1))
    }
}