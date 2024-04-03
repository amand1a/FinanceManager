package com.example.financemanager.data.mapper

import com.example.financemanager.data.dataBase.entity.ExpensesEntity
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.ExpenseDto
import javax.inject.Inject

class ExpenseDtoMapper @Inject constructor() : ModelMapper<ExpensesEntity, ExpenseDto> {
    override fun map(source: ExpensesEntity): ExpenseDto {
        return ExpenseDto(source.id, source.category, source.date, source.description, source.value)
    }
}