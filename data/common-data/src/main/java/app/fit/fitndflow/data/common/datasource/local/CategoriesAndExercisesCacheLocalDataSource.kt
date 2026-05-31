package app.fit.fitndflow.data.common.datasource.local

import com.fit.fitndflow.app.domain.common.models.CategoryModel


class CategoriesAndExercisesCacheLocalDataSource {
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