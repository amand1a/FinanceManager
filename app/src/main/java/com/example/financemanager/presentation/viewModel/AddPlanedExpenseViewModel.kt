package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanager.common.constants.ArrayOfExpenses
import com.example.financemanager.common.extension.getLocalDateTimeFroMillisecond
import com.example.financemanager.presentation.model.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddPlanedExpenseViewModel @Inject constructor() : ViewModel() {
    private val _addExpensesState = MutableStateFlow(
        AddPlanedExpensesState(
            ArrayOfExpenses[0], LocalDateTime.now(), "", ""
        )
    )
    val state = _addExpensesState.asStateFlow()

    fun submitPlannedExpense() {
        viewModelScope.launch {

        }
        _addExpensesState.value = AddPlanedExpensesState(
            ArrayOfExpenses[0], LocalDateTime.now(), "", ""
        )
    }

    fun clearExpenseFields() {
        _addExpensesState.value = AddPlanedExpensesState(
            ArrayOfExpenses[0], LocalDateTime.now(), "", ""
        )
    }

    fun changeExpenseCategory(expensesCategories: CategoryModel) {
        _addExpensesState.update {
            it.copy(category = expensesCategories)
        }
    }

    fun changeDateOfExpense(localDateTime: Long) {
        _addExpensesState.update {
            it.copy(date = localDateTime.getLocalDateTimeFroMillisecond())
        }
    }

    fun changeCostValue(value: String) {
        _addExpensesState.update {
            it.copy(value = value)
        }
    }

    fun changeDescription(value: String) {
        _addExpensesState.update {
            it.copy(description = value)
        }
    }

    fun changeTime(hour: Int, minute: Int){
        _addExpensesState.update {
            it.copy(
                date = it.date.withHour(hour).withMinute(minute).withSecond(0)
            )
        }
    }
}

data class AddPlanedExpensesState(
    val category: CategoryModel,
    val date: LocalDateTime,
    val description: String,
    val value: String
)