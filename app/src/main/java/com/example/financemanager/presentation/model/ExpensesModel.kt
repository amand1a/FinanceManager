package com.example.financemanager.presentation.model

import java.time.LocalDateTime

data class ExpensesModel(
    val id: Int,
    val category: ExpensesCategories,
    val date: LocalDateTime,
    val description: String,
    val value: Double
)