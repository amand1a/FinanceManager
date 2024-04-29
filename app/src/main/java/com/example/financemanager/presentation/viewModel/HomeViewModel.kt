package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.ExpenseDto
import com.example.financemanager.domain.repository.ExpensesRepository
import com.example.financemanager.domain.repository.SettingsRepository
import com.example.financemanager.presentation.model.CategoryExpensesHomeModel
import com.example.financemanager.presentation.model.ExpensesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expenseRepository: ExpensesRepository,
    private val expenseDtoToExpenseModelMapper: ModelMapper<ExpenseDto, ExpensesModel>,
    private val expensesListToCategoryExpensesHomeModelList: ModelMapper<List<ExpensesModel>, List<CategoryExpensesHomeModel>>,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<HomeUiState>(HomeUiState.Fetching(LocalDateTime.now(), ""))
    val uiState = _uiState.asStateFlow()

    fun start(localDateTime: LocalDateTime) {
        _uiState.value = HomeUiState.Fetching(localDateTime, "")
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                expenseRepository.getMonthlyExpenses(_uiState.value.getLocalDate()).collect {
                    val listOfExpensesModel =
                        it.map { expenseDto -> expenseDtoToExpenseModelMapper.map(expenseDto) }
                    _uiState.value = HomeUiState.LoadedHomeData(
                        selectedMoth = _uiState.value.getLocalDate(),
                        totalCost = getTotalCost(listOfExpensesModel),
                        categories = expensesListToCategoryExpensesHomeModelList.map(
                            listOfExpensesModel
                        ),
                        currencyName = _uiState.value.getCurrencyNameM()
                    )
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    _uiState.value.getLocalDate(),
                    _uiState.value.getCurrencyNameM()
                )
            }
        }
        viewModelScope.launch {
            try {
                settingsRepository.getNameCurrency().collect {
                    when (_uiState.value) {
                        is HomeUiState.LoadedHomeData -> {
                            _uiState.value = (_uiState.value as HomeUiState.LoadedHomeData).copy(
                                currencyName = it
                            )
                        }

                        is HomeUiState.Fetching -> {
                            _uiState.value =
                                (_uiState.value as HomeUiState.Fetching).copy(currencyName = it)
                        }

                        is HomeUiState.Error -> {
                            _uiState.value =
                                (_uiState.value as HomeUiState.Error).copy(currencyName = it)
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    _uiState.value.getLocalDate(),
                    _uiState.value.getCurrencyNameM()
                )
            }
        }
    }

    fun reload() {
        fetchData()
    }

    private fun getTotalCost(expensesList: List<ExpensesModel>): Double {
        var totalCost = 0.0
        for (elem in expensesList) {
            totalCost += elem.value
        }
        return totalCost
    }
}

sealed interface HomeUiState {
    data class Fetching(
        val localDateTime: LocalDateTime,
        val currencyName: String
    ) : HomeUiState {
        override fun getLocalDate(): LocalDateTime {
            return localDateTime
        }

        override fun getCurrencyNameM(): String {
            return currencyName
        }
    }

    data class LoadedHomeData(
        val selectedMoth: LocalDateTime,
        val totalCost: Double,
        val categories: List<CategoryExpensesHomeModel>,
        val currencyName: String
    ) : HomeUiState {
        override fun getLocalDate(): LocalDateTime {
            return this.selectedMoth
        }

        override fun getCurrencyNameM(): String {
            return currencyName
        }
    }

    data class Error(
        val localDateTime: LocalDateTime,
        val currencyName: String
    ) : HomeUiState {
        override fun getLocalDate(): LocalDateTime {
            return localDateTime
        }

        override fun getCurrencyNameM(): String {
            return currencyName
        }
    }

    fun getLocalDate(): LocalDateTime
    fun getCurrencyNameM(): String
}

