package com.example.financemanager.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.financemanager.domain.model.CategoryDto
import java.time.LocalDateTime

@Entity(tableName = "expenses")
data class ExpensesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: CategoryDto,
    val date: LocalDateTime,
    val description: String,
    val value: Double
)