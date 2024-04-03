package com.example.financemanager.domain.model

import java.time.LocalDateTime

data class ExpenseDto(
    val id: Int,
    val category: CategoryDto,
    val date: LocalDateTime,
    val description: String,
    val value: Double
)