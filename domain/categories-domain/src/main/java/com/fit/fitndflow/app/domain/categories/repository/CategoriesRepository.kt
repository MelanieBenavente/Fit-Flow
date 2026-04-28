package com.fit.fitndflow.app.domain.categories.repository

import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.repository.CommonRepository

interface CategoriesRepository : CommonRepository {
    suspend fun getCategoryList(): List<CategoryModel>
    suspend fun addNewCategory(categoryName: String, language: String): List<CategoryModel>
    suspend fun modifyCategory(
        categoryName: String,
        language: String,
        categoryId: Int,
        imageUrl: String?
    ): List<CategoryModel>
    suspend fun deleteCategory(integer: Int): List<CategoryModel>

    suspend fun migrateToLocal()

    suspend fun createInitialData()

    fun isInitialDataCreated(): Boolean
}
