package com.example.financemanager.data.repository

import com.example.financemanager.data.dataBase.dao.ExpensesDao
import com.example.financemanager.data.dataBase.entity.ExpensesEntity
import com.example.financemanager.data.mapper.ExpenseDtoMapper
import com.example.financemanager.data.model.CategoryDataModel
import com.example.financemanager.domain.model.ExpenseDto
import com.example.financemanager.domain.repository.ExpensesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesDao: ExpensesDao,
    private val expenseDtoMapper: ExpenseDtoMapper
) : ExpensesRepository {
    override fun getMonthlyExpenses(date: LocalDateTime): Flow<List<ExpenseDto>> {
        return expensesDao.getAllExpenses(date.format(DateTimeFormatter.ofPattern("yyyy-MM"))).map {
            it.map { elem ->
                expenseDtoMapper.map(elem)
            }
        }
    }

    override suspend fun addExpense(expense: ExpenseDto) {
        expensesDao.insertExpense(
            ExpensesEntity(
                id = null,
                category = CategoryDataModel(
                    title = expense.category.title,
                    icon = expense.category.icon,
                    containerColor = expense.category.containerColor
                ),
                date = expense.date,
                description = expense.description,
                value = expense.value
            )
        )
    }
}