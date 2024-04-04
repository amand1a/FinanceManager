package com.example.financemanager.di.modules

import com.example.financemanager.data.dataBase.entity.ExpensesEntity
import com.example.financemanager.data.mapper.ExpenseDtoMapper
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.CategoryDto
import com.example.financemanager.domain.model.ExpenseDto
import com.example.financemanager.presentation.mapper.CategoryDtoToCategoryModelMapper
import com.example.financemanager.presentation.mapper.CategoryModelToCategoryDtoMapper
import com.example.financemanager.presentation.mapper.ExpenseDtoToExpenseModelMapper
import com.example.financemanager.presentation.model.CategoryModel
import com.example.financemanager.presentation.model.ExpensesModel
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

    @Provides
    fun getCategoryDtoToCategoryModelMapper(
        impl: CategoryDtoToCategoryModelMapper
    ): ModelMapper<CategoryDto, CategoryModel> {
        return impl
    }

    @Provides
    fun getExpensesDtoToExpensesModelMapper(
        impl: ExpenseDtoToExpenseModelMapper
    ): ModelMapper<ExpenseDto, ExpensesModel> {
        return impl
    }

    @Provides
    fun getCategoryModelToCategoryDtoMapper(
        impl: CategoryModelToCategoryDtoMapper
    ): ModelMapper<CategoryModel,CategoryDto> {
        return impl
    }
}