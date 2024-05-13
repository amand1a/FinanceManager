package com.example.financemanager.data.repository

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financemanager.data.services.PlannedExpenditureWorkManager
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
    @ApplicationContext context: Context
): PlannedExpenseRepository {

    private val workManager = WorkManager.getInstance(context)

    override fun plannedExpense(expense: ExpenseDto, title: String) {

        val data = Data.Builder().putString(
            "title", title
        ).putString("category" , expense.category).build()
        val timeNow = LocalDateTime.now()
        val durationTime = expense.date.getTimeInMillisecond() - timeNow.getTimeInMillisecond()
        val workBuilder = OneTimeWorkRequestBuilder<PlannedExpenditureWorkManager>().setInitialDelay(
            Duration.ofMillis(durationTime))
            .setInputData(data)
            .build()
        workManager.enqueue(workBuilder)
    }
}