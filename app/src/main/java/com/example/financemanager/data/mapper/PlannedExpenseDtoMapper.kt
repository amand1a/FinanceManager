package com.example.financemanager.data.mapper

import androidx.room.Insert
import com.example.financemanager.data.dataBase.entity.PlannedExpenseEntity
import com.example.financemanager.data.model.CategoryDataModel
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.CategoryDto
import com.example.financemanager.domain.model.ExpenseDto
import javax.inject.Inject

class PlannedExpenseDtoMapper @Inject constructor(
    private val categoryDtoMapper: ModelMapper<CategoryDataModel, CategoryDto>
): ModelMapper<PlannedExpenseEntity, ExpenseDto> {
    override fun map(source: PlannedExpenseEntity): ExpenseDto {
        return ExpenseDto(
            source.id ?: 0,
            categoryDtoMapper.map(source.category),
            source.date,
            source.description,
            source.value
        )
    }
}