package com.example.financemanager.presentation.model

import androidx.compose.ui.graphics.Color
import com.example.financemanager.R

data class ExpensesCategories(
    val title: String,
    val icon: Int,
    val containerColor: Color,
)


val ArrayOfExpenses = listOf<ExpensesCategories>(
    ExpensesCategories("Food & Drinks", R.drawable.baseline_fastfood_24, Color(255, 152, 0, 255)),
    ExpensesCategories("Shopping", R.drawable.shopping_expense_icon, Color(3, 169, 244, 255)),
    ExpensesCategories(
        "Transportation",
        R.drawable.baseline_directions_bus_24,
        Color(240, 98, 146, 255)
    ),
    ExpensesCategories("Vehicle", R.drawable.vehicle_expense_icon, Color(130, 119, 23, 255)),
    ExpensesCategories("Medicine", R.drawable.medical_expense_icon, Color(244, 67, 54, 255)),
    ExpensesCategories(
        "Entertainment",
        R.drawable.entertainment_expenses,
        Color(156, 39, 176, 255)
    ),
    ExpensesCategories(
        "Communication, Electronics", R.drawable.communication_pc_expenses_icon, Color(
            76,
            175,
            80,
            255
        )
    ),
    ExpensesCategories("Taxes", R.drawable.taxes_icon, Color(255, 235, 59, 255)),
    ExpensesCategories("House", R.drawable.house_expence_icon, Color(81, 45, 168, 255)),
    ExpensesCategories("Other", R.drawable.others_expenses_icon, Color(100, 255, 218, 255)),
)