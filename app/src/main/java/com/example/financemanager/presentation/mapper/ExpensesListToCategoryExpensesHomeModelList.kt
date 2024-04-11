package com.example.financemanager.presentation.mapper

import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.presentation.model.CategoryExpensesHomeModel
import com.example.financemanager.presentation.model.CategoryModel
import com.example.financemanager.presentation.model.ExpensesModel
import javax.inject.Inject

class ExpensesListToCategoryExpensesHomeModelList @Inject constructor() :
    ModelMapper<List<ExpensesModel>, List<CategoryExpensesHomeModel>> {
    override fun map(source: List<ExpensesModel>): List<CategoryExpensesHomeModel> {
        val categoryExpensesList = mutableListOf<CategoryExpensesHomeModel>()
        val categoryExpensesMap = mutableMapOf<CategoryModel, MutableList<ExpensesModel>>()
        for (expense in source) {
            val category = expense.category
            if (categoryExpensesMap.containsKey(category)) {
                categoryExpensesMap[category]?.add(expense)
            } else {
                categoryExpensesMap[category] = mutableListOf(expense)
            }
        }
        for ((category, expenses) in categoryExpensesMap) {
            val costCategory = expenses.sumOf { it.value }.toFloat()
            val percentCostCategory = costCategory / source.sumOf { it.value }.toFloat() * 100
            val categoryExpenses = CategoryExpensesHomeModel(
                category = category,
                costCategory = costCategory,
                percentCostCategory = percentCostCategory,
                expenses = expenses
            )
            categoryExpensesList.add(categoryExpenses)
        }
        return categoryExpensesList
    }
}