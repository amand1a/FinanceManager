package com.example.financemanager.presentation.model

data class CategoryExpensesHomeModel(
    val category: CategoryModel,
    val costCategory: Float,
    val percentCostCategory: Float,
    val expenses: List<ExpensesModel>
)
