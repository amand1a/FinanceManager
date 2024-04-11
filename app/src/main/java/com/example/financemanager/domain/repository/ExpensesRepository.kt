package com.example.financemanager.domain.repository

import com.example.financemanager.domain.model.ExpenseDto
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ExpensesRepository {
    fun getMonthlyExpenses(date: LocalDateTime): Flow<List<ExpenseDto>>

    suspend fun addExpense(expense: ExpenseDto)
}