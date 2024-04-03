package com.example.financemanager.di.modules

import com.example.financemanager.data.dataBase.entity.ExpensesEntity
import com.example.financemanager.data.mapper.ExpenseDtoMapper
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.ExpenseDto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Provides
    fun getExpenseDtoMapper(impl: ExpenseDtoMapper): ModelMapper<ExpensesEntity, ExpenseDto> {
        return impl
    }
}