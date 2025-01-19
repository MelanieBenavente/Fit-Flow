package com.fit.fitndflow.app.domain.categories.repository

import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.repository.CommonRepository

interface CategoriesRepository : CommonRepository {
    @get:Throws(Exception::class)
    val categoryList: List<CategoryModel?>?
    @Throws(Exception::class)
    fun addNewCategory(categoryName: String?, language: String?): List<CategoryModel?>?
    @Throws(Exception::class)
    fun modifyCategory(
        categoryName: String?,
        language: String?,
        categoryId: Int,
        imageUrl: String?
    ): List<CategoryModel?>?

    @Throws(Exception::class)
    fun deleteCategory(integer: Int?): List<CategoryModel?>?
}
