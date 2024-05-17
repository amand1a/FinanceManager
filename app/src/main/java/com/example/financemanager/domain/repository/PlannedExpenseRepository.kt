package com.example.financemanager.domain.repository

import com.example.financemanager.domain.model.ExpenseDto

interface PlannedExpenseRepository {
    suspend fun plannedExpense(expense: ExpenseDto, title: String)

    suspend fun addPlannedExpenseInExpense(id: Long)
}