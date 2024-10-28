package com.fit.fitndflow.data.datasource

import app.fit.fitndflow.domain.model.CategoryModel

class LocalDataSource {
    private var categoryListCachedResponse : List<CategoryModel>? = null
    private var trainingResponseCacheByDate : HashMap<String, List<CategoryModel>> = hashMapOf()
    var currentDate: String? = null
    fun replaceAllDataFromCategoryListCache(categoryList: List<CategoryModel>?) {
        categoryListCachedResponse = categoryList
    }

    fun getAvailableCategoryListCache() : List<CategoryModel>? {
        return categoryListCachedResponse
    }

    fun replaceAllDataFromTrainingCache(date: String, categoryList: List<CategoryModel>) {
        trainingResponseCacheByDate.put(date, categoryList)
    }

    fun getAvailableTrainingListCache() : HashMap<String, List<CategoryModel>> {
        return trainingResponseCacheByDate
    }

}