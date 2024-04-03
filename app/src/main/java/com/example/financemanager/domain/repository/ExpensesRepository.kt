package com.example.financemanager.domain.repository

import com.example.financemanager.domain.model.ExpenseDto
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {

    fun getMonthlyExpenses(): Flow<List<ExpenseDto>>

    suspend fun addExpense(expense: ExpenseDto)
}