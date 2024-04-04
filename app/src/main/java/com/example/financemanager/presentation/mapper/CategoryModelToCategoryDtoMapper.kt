package com.example.financemanager.presentation.mapper

import com.example.financemanager.domain.mapper.ModelMapper
import com.example.financemanager.domain.model.CategoryDto
import com.example.financemanager.presentation.model.CategoryModel
import javax.inject.Inject

class CategoryModelToCategoryDtoMapper @Inject constructor() :
    ModelMapper<CategoryModel, CategoryDto> {
    override fun map(source: CategoryModel): CategoryDto {
        return CategoryDto(
            icon = source.icon,
            title = source.title,
            containerColor = source.containerColor
        )
    }
}