package com.example.financemanager.domain.mapper

import com.example.financemanager.domain.model.CategoryDto
import com.example.financemanager.presentation.model.CategoryModel
import javax.inject.Inject

class CategoryDtoToCategoryModelMapper @Inject constructor() :
    ModelMapper<CategoryDto, CategoryModel> {
    override fun map(source: CategoryDto): CategoryModel {
        return CategoryModel(
            icon = source.icon,
            title = source.title,
            containerColor = source.containerColor
        )
    }
}