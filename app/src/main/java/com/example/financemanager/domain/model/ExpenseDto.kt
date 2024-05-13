package com.example.financemanager.domain.model

import java.io.Serializable
import java.time.LocalDateTime


data class ExpenseDto(
    val id: Long = 0,
    val category: CategoryDto,
    val date: LocalDateTime,
    val description: String,
    val value: Double
)