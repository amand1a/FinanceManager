package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.financemanager.domain.repository.ExpensesRepository
import com.example.financemanager.presentation.model.CategoryModel
import com.example.financemanager.presentation.model.ExpensesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expenseRepository: ExpensesRepository
) : ViewModel() {

}


data class HomeUiState(
    val selectedMoth: LocalDateTime,
    val totalCost: Float,
    val categories: List<CategoryExpensesHomeModel>
)

data class CategoryExpensesHomeModel(
    val category: CategoryModel,
    val costCategory: Float,
    val percentCostCategory: Float,
    val expenses: List<ExpensesModel>
)

