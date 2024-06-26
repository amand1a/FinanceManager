package com.example.financemanager.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.financemanager.data.dataBase.converter.CategoryConverter
import com.example.financemanager.data.dataBase.converter.LocalDateTimeConverter
import com.example.financemanager.data.dataBase.dao.ExpensesDao
import com.example.financemanager.data.dataBase.dao.PlannedExpenseDao
import com.example.financemanager.data.dataBase.entity.ExpensesEntity
import com.example.financemanager.data.dataBase.entity.PlannedExpenseEntity

@Database(
    entities = [ExpensesEntity::class, PlannedExpenseEntity::class],
    version = (2),
    exportSchema = false
)
@TypeConverters(
    LocalDateTimeConverter::class,
    CategoryConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expensesDao(): ExpensesDao
    abstract fun plannedExpenseDao(): PlannedExpenseDao

    companion object {
        private const val DB_NAME = "app.db"

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context?) = instance ?: synchronized(LOCK) {
            context?.let {
                buildDatabase(it).apply {
                    instance = this
                }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}