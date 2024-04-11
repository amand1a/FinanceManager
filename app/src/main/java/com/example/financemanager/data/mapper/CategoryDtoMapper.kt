package com.example.financemanager.data.mapper

import com.example.financemanager.data.model.CategoryDataModel
import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.CategoryDto
import javax.inject.Inject

class CategoryDtoMapper @Inject constructor(): ModelMapper<CategoryDataModel, CategoryDto> {
    override fun map(source: CategoryDataModel): CategoryDto {
        return CategoryDto(title = source.title,
            icon = source.icon,
            containerColor = source.containerColor)
    }
}