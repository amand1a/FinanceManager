package com.example.financemanager.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.financemanager.data.dataBase.entity.ExpensesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {
    @Insert
    suspend fun insertExpense(expensesEntity: ExpensesEntity)

    @Query("SELECT * FROM expenses WHERE strftime('%Y-%m', date) LIKE :month ")
    fun getAllExpenses(month: String): Flow<List<ExpensesEntity>>
}