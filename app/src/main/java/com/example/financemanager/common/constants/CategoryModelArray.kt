package com.example.financemanager.common.constants

import androidx.compose.ui.graphics.Color
import com.example.financemanager.R
import com.example.financemanager.presentation.model.CategoryModel

val ArrayOfExpenses = listOf(
    CategoryModel(
        title = R.string.food_drinks,
        icon = R.drawable.baseline_fastfood_24,
        containerColor = Color(255, 152, 0, 255)
    ),
    CategoryModel(
        title = R.string.shopping,
        icon = R.drawable.shopping_expense_icon,
        containerColor = Color(3, 169, 244, 255)
    ),
    CategoryModel(
        title = R.string.transportation,
        icon = R.drawable.baseline_directions_bus_24,
        containerColor = Color(240, 98, 146, 255)
    ),
    CategoryModel(
        title = R.string.vehicle,
        icon = R.drawable.vehicle_expense_icon,
        containerColor = Color(130, 119, 23, 255)
    ),
    CategoryModel(
        title = R.string.medicine,
        icon = R.drawable.medical_expense_icon,
        containerColor = Color(244, 67, 54, 255)
    ),
    CategoryModel(
        title = R.string.entertainment,
        icon = R.drawable.entertainment_expenses,
        containerColor = Color(156, 39, 176, 255)
    ),
    CategoryModel(
        title = R.string.communication_electronics,
        icon = R.drawable.communication_pc_expenses_icon,
        containerColor = Color(
            76,
            175,
            80,
            255
        )
    ),
    CategoryModel(
        title = R.string.taxes,
        icon = R.drawable.taxes_icon,
        containerColor = Color(255, 235, 59, 255)
    ),
    CategoryModel(
        title = R.string.house,
        icon = R.drawable.house_expence_icon,
        containerColor = Color(81, 45, 168, 255)
    ),
    CategoryModel(
        title = R.string.other,
        icon = R.drawable.others_expenses_icon,
        containerColor = Color(100, 255, 218, 255)
    ),
)