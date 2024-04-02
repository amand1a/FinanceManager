package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.financemanager.presentation.model.ArrayOfExpenses
import com.example.financemanager.presentation.model.ExpensesCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor() : ViewModel() {
    private val _addExpensesState = MutableStateFlow(
        AddExpensesState(
            ArrayOfExpenses[0], LocalDateTime.now(), "", ""
        )
    )
    val state = _addExpensesState.asStateFlow()

    fun addExpenseInDB() {
        _addExpensesState.value = AddExpensesState(
            ArrayOfExpenses[0], LocalDateTime.now(), "", ""
        )
    }

    fun clearExpenseFields() {
        _addExpensesState.value = AddExpensesState(
            ArrayOfExpenses[0], LocalDateTime.now(), "", ""
        )
    }

    fun changeExpenseCategory(expensesCategories: ExpensesCategories) {
        _addExpensesState.update {
            it.copy(category = expensesCategories)
        }
    }

    fun changeDateOfExpense(localDateTime: Long) {
        _addExpensesState.update {
            it.copy(date = getLocalDateTimeFroMillisecond(localDateTime))
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


    private fun getLocalDateTimeFroMillisecond(value: Long): LocalDateTime {
        return Instant.ofEpochMilli(value)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

}

data class AddExpensesState(
    val category: ExpensesCategories,
    val date: LocalDateTime,
    val description: String,
    val value: String
)

fun LocalDateTime.getTimeInMillisecond(): Long {
    val zonedDateTime = this.atZone(ZoneId.systemDefault())
    return zonedDateTime.toInstant().toEpochMilli()
}




