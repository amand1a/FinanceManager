package com.example.financemanager.data.repository

import com.example.financemanager.data.dataBase.dao.ExpensesDao
import com.example.financemanager.data.dataBase.entity.ExpensesEntity
import com.example.financemanager.data.mapper.ExpenseDtoMapper
import com.example.financemanager.domain.model.ExpenseDto
import com.example.financemanager.domain.repository.ExpensesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesDao: ExpensesDao,
    private val expenseDtoMapper: ExpenseDtoMapper
) : ExpensesRepository {
    override fun getMonthlyExpenses(): Flow<List<ExpenseDto>> {
        return expensesDao.getAllExpenses().map {
            it.map { elem ->
                expenseDtoMapper.map(elem)
            }
        }
    }

    override suspend fun addExpense(expense: ExpenseDto) {
        expensesDao.insertExpense(
            ExpensesEntity(
                id = expense.id,
                category = expense.category,
                date = expense.date,
                description = expense.description,
                value = expense.value
            )
        )
    }
}