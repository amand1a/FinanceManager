package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.ExpenseDto
import com.example.financemanager.domain.repository.ExpensesRepository
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
    private val expensesListToCategoryExpensesHomeModelList: ModelMapper<List<ExpensesModel>, List<CategoryExpensesHomeModel>>
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Fetching(LocalDateTime.now()))
    val uiState = _uiState.asStateFlow()

    fun start(localDateTime: LocalDateTime) {
        _uiState.value = HomeUiState.Fetching(localDateTime)
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
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(_uiState.value.getLocalDate())
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
    data class Fetching(val localDateTime: LocalDateTime) : HomeUiState {
        override fun getLocalDate(): LocalDateTime {
            return localDateTime
        }
    }

    data class LoadedHomeData(
        val selectedMoth: LocalDateTime,
        val totalCost: Double,
        val categories: List<CategoryExpensesHomeModel>,
    ) : HomeUiState {
        override fun getLocalDate(): LocalDateTime {
            return this.selectedMoth
        }
    }

    data class Error(val localDateTime: LocalDateTime) : HomeUiState {
        override fun getLocalDate(): LocalDateTime {
            return localDateTime
        }
    }

    fun getLocalDate(): LocalDateTime
}

