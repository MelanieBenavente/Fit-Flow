package app.fit.fitndflow.data.common.datasource.local

import com.fit.fitndflow.app.domain.common.models.CategoryModel


class TrainingCacheLocalDataSource {
    private var trainingResponseCacheByDate : HashMap<String, List<CategoryModel>> = hashMapOf()
    var currentDate: String? = null


    fun replaceAllDataFromTrainingCache(date: String, categoryList: List<CategoryModel>) {
        trainingResponseCacheByDate.put(date, categoryList)
    }

    fun getTrainingsByDate(date: String) : List<CategoryModel>? {
        return trainingResponseCacheByDate.get(date)
    }

    fun cleanCache() {
        trainingResponseCacheByDate.clear()
    }

}