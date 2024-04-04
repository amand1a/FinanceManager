package com.example.financemanager.presentation.mapper

import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.ExpenseDto
import com.example.financemanager.presentation.model.ExpensesModel
import javax.inject.Inject

class ExpenseDtoToExpenseModelMapper @Inject constructor(
    private val categoryDtoToCategoryModelMapper: CategoryDtoToCategoryModelMapper
) : ModelMapper<ExpenseDto, ExpensesModel> {
    override fun map(source: ExpenseDto): ExpensesModel {
        return ExpensesModel(
            id = source.id,
            category = categoryDtoToCategoryModelMapper.map(source.category),
            date = source.date,
            description = source.description,
            value = source.value
        )
    }
}