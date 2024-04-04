package com.example.financemanager.data.dataBase.converter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter
import com.example.financemanager.domain.model.CategoryDto

class CategoryConverter {
    @TypeConverter
    fun fromCategory(category: CategoryDto): String {
        return category.icon.toString() + "|" + category.title.toString() + "|" + category.containerColor.toArgb()
            .toString()
    }

    @TypeConverter
    fun toCategory(categoryString: String): CategoryDto {
        val parts = categoryString.split("|")
        val icon = parts[0].toInt()
        val title = parts[1].toInt()
        val containerColor = Color(parts[2].toInt())
        return CategoryDto(title, icon, containerColor)
    }
}