package app.fit.fitndflow.data.common.datasource

import app.fit.fitndflow.domain.model.CategoryModel

class CategoriesAndExercisesLocalDataSource {
    private var categoryListCachedResponse : List<CategoryModel>? = null

    fun replaceAllDataFromCategoryListCache(categoryList: List<CategoryModel>) {
        categoryListCachedResponse = categoryList
    }

    fun getAvailableCategoryListCache() : List<CategoryModel>? {
        return categoryListCachedResponse
    }
    fun cleanCache() {
        categoryListCachedResponse = null
    }
}