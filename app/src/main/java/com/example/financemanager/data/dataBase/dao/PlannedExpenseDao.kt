package com.example.financemanager.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.financemanager.data.dataBase.entity.PlannedExpenseEntity

@Dao
interface PlannedExpenseDao {
    @Insert
    suspend fun insert(plannedExpense: PlannedExpenseEntity): Long

    @Query("SELECT * FROM plannedExpenses WHERE id = :id")
    suspend fun get(id: Long): PlannedExpenseEntity?

    @Query("DELETE FROM plannedExpenses WHERE id = :id")
    suspend fun delete(id: Long): Int
}
