package com.example.financemanager.domain.repository

import com.example.financemanager.domain.model.ExpenseDto

interface PlannedExpenseRepository {

    fun plannedExpense(expense: ExpenseDto, title: String)
}