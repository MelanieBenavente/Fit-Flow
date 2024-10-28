package com.fit.fitndflow.data.datasource

import app.fit.fitndflow.domain.model.CategoryModel

class CategoriesAndExercisesLocalDataSource {
    private var categoryListCachedResponse : List<CategoryModel>? = null

    fun replaceAllDataFromCategoryListCache(categoryList: List<CategoryModel>?) {
        categoryListCachedResponse = categoryList
    }

    fun getAvailableCategoryListCache() : List<CategoryModel>? {
        return categoryListCachedResponse
    }
}