package com.example.financemanager.data.mapper

import com.example.financemanager.data.dataBase.entity.ExpensesEntity
import com.example.financemanager.data.model.CategoryDataModel
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.CategoryDto
import com.example.financemanager.domain.model.ExpenseDto
import javax.inject.Inject

class ExpenseDtoMapper @Inject constructor(
    private val categoryDtoMapper: ModelMapper<CategoryDataModel, CategoryDto>
) : ModelMapper<ExpensesEntity, ExpenseDto> {
    override fun map(source: ExpensesEntity): ExpenseDto {
        return ExpenseDto(
            source.id!!,
            categoryDtoMapper.map(source.category),
            source.date,
            source.description,
            source.value
        )
    }
}