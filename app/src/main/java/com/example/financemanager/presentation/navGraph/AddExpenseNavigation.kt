package com.example.financemanager.presentation.navGraph

import android.content.Context
import com.example.financemanager.R

sealed class ExpenseNavigation(val title: Int){
    data object AddExpense: ExpenseNavigation(R.string.expense)
    data object PlannedExpense: ExpenseNavigation(R.string.planned_expense)
}

fun getExpenseNavItems(context: Context): List<ExpenseNavItem>{
    return listOf(
        ExpenseNavItem(context.getString(R.string.expense)),
        ExpenseNavItem(context.getString(R.string.planned_expense)),
    )
}

data class ExpenseNavItem(
    val title: String
)