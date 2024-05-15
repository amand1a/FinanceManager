package com.example.financemanager.data.repository

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financemanager.data.dataBase.dao.PlannedExpenseDao
import com.example.financemanager.data.dataBase.entity.PlannedExpenseEntity
import com.example.financemanager.data.model.CategoryDataModel
import com.example.financemanager.data.services.PlannedExpenditureWorkManager
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.ExpenseDto
import com.example.financemanager.domain.repository.ExpensesRepository
import com.example.financemanager.domain.repository.PlannedExpenseRepository
import com.example.financemanager.presentation.viewModel.getTimeInMillisecond
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

class PlannedExpenseRepositoryImpl @Inject constructor(
    private val expenseRepository: ExpensesRepository,
    private val plannedExpenseDao: PlannedExpenseDao,
    private val plannedExpenseDtoMapper: ModelMapper<PlannedExpenseEntity, ExpenseDto>,
    @ApplicationContext context: Context
) : PlannedExpenseRepository {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun plannedExpense(expense: ExpenseDto, title: String) {
        val idInserted = insertPlannedExpenseInDb(expense)
        val data = Data.Builder().putString(
            "title", title
        ).putLong("id", idInserted)
            .putString("description", expense.description)
            .putString("cost", expense.value.toString())
            .build()
        val timeNow = LocalDateTime.now()
        val durationTime = expense.date.getTimeInMillisecond() - timeNow.getTimeInMillisecond()
        val workBuilder =
            OneTimeWorkRequestBuilder<PlannedExpenditureWorkManager>().setInitialDelay(
                Duration.ofMillis(durationTime)
            )
                .setInputData(data)
                .build()
        workManager.enqueue(workBuilder)
    }

    override suspend fun addPlannedExpenseInExpense(id: Long) {
        val plannedExpense = getAndDeletePlannedExpense(id)
        if (plannedExpense != null) {
            expenseRepository.addExpense(plannedExpenseDtoMapper.map(plannedExpense))
        }
    }

    private suspend fun insertPlannedExpenseInDb(expense: ExpenseDto): Long {
        return plannedExpenseDao.insert(
            PlannedExpenseEntity(
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


    private suspend fun getAndDeletePlannedExpense(id: Long): PlannedExpenseEntity? {
        val exp = plannedExpenseDao.get(id)
        return if (exp != null) {
            plannedExpenseDao.delete(id)
            exp
        } else {
            null
        }
    }
}