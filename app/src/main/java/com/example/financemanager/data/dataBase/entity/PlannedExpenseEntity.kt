package com.example.financemanager.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.financemanager.data.model.CategoryDataModel
import java.time.LocalDateTime

@Entity(tableName = "plannedExpenses")
data class PlannedExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val category: CategoryDataModel,
    val date: LocalDateTime,
    val description: String,
    val value: Double
)
