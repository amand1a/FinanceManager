package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.ExpenseDto
import com.example.financemanager.domain.repository.ExpensesRepository
import com.example.financemanager.presentation.model.CategoryModel
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
    private val expenseDtoToExpenseModelMapper: ModelMapper<ExpenseDto, ExpensesModel>
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Fetching(LocalDateTime.now()))
    val uiState = _uiState.asStateFlow()

    init {
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
                        categories = convertExpensesToCategoryExpenses(listOfExpensesModel)
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

    fun getNextMoth() {
        _uiState.value = HomeUiState.Fetching(_uiState.value.getLocalDate().plusMonths(1))
        fetchData()
    }

    fun getPrevMoth() {
        _uiState.value = HomeUiState.Fetching(_uiState.value.getLocalDate().minusMonths(1))
        fetchData()
    }

    private fun getTotalCost(expensesList: List<ExpensesModel>): Double {
        var totalCost = 0.0
        for (elem in expensesList) {
            totalCost += elem.value
        }
        return totalCost
    }

    private fun convertExpensesToCategoryExpenses(expensesList: List<ExpensesModel>): List<CategoryExpensesHomeModel> {
        val categoryExpensesList = mutableListOf<CategoryExpensesHomeModel>()
        val categoryExpensesMap = mutableMapOf<CategoryModel, MutableList<ExpensesModel>>()
        for (expense in expensesList) {
            val category = expense.category
            if (categoryExpensesMap.containsKey(category)) {
                categoryExpensesMap[category]?.add(expense)
            } else {
                categoryExpensesMap[category] = mutableListOf(expense)
            }
        }
        for ((category, expenses) in categoryExpensesMap) {
            val costCategory = expenses.sumOf { it.value }.toFloat()
            val percentCostCategory = costCategory / expensesList.sumOf { it.value }.toFloat() * 100
            val categoryExpenses = CategoryExpensesHomeModel(
                category = category,
                costCategory = costCategory,
                percentCostCategory = percentCostCategory,
                expenses = expenses
            )
            categoryExpensesList.add(categoryExpenses)
        }
        return categoryExpensesList
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

data class CategoryExpensesHomeModel(
    val category: CategoryModel,
    val costCategory: Float,
    val percentCostCategory: Float,
    val expenses: List<ExpensesModel>
)

