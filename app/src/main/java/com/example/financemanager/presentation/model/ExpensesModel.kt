package com.example.financemanager.presentation.model

import java.time.LocalDateTime

data class ExpensesModel(
    val id: Long,
    val category: CategoryModel,
    val date: LocalDateTime,
    val description: String,
    val value: Double
)