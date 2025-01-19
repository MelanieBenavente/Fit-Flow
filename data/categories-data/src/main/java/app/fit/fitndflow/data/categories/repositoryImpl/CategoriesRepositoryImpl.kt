package app.fit.fitndflow.data.categories.repositoryImpl

import android.content.Context
import app.fit.fitndflow.data.categories.datasource.remote.CategoryRemoteDataSource
import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource
import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.mapper.CategoryModelMapperKt.Companion.toModel
import app.fit.fitndflow.data.common.mapper.convertToStringInLanguages
import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.models.CategoryModel

class CategoriesRepositoryImpl(
    private val mContext: Context,
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryDao: CategoryDao,
    private val categoriesAndExercisesLocalDataSource: CategoriesAndExercisesLocalDataSource,
    private val trainingLocalDataSource: TrainingLocalDataSource
) : CategoriesRepository {
    private val isLocalMode = true

    @Throws(Exception::class)
    override fun getCategoryList(): List<CategoryModel> {
        if (categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache() == null) {
            val response: List<CategoryModel>
            try {
                if (isLocalMode) {
                    response = categoryDao.getAllCategories()
                } else {
                    response = categoryRemoteDataSource.getCategoryList()
                }
                categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(response)
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(e)
            }
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache()!!
    }

    @Throws(Exception::class)
    override fun addNewCategory(categoryName: String, language: String): List<CategoryModel> {
        val stringInLanguages = convertToStringInLanguages(language, categoryName)
        val response: List<CategoryDto>
        try {
            response = categoryRemoteDataSource.addNewCategory(stringInLanguages)
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(
                toModel(
                    response
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache()!!
    }

    @Throws(Exception::class)
    override fun modifyCategory(
        categoryName: String,
        language: String,
        categoryId: Int,
        imageUrl: String
    ): List<CategoryModel> {
        val stringInLanguages = convertToStringInLanguages(language, categoryName)
        val response: List<CategoryDto>
        try {
            response =
                categoryRemoteDataSource.modifyCategory(stringInLanguages, categoryId, imageUrl)
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(
                toModel(
                    response
                )
            )
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache()!!
    }

    @Throws(Exception::class)
    override fun deleteCategory(categoryId: Int): List<CategoryModel> {
        val response: List<CategoryDto>
        try {
            response = categoryRemoteDataSource.deleteCategory(categoryId)
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(
                toModel(
                    response
                )
            )
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache()!!
    }
}
