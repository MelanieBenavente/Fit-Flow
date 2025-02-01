package app.fit.fitndflow.data.categories.repositoryImpl

import android.content.Context
import app.fit.fitndflow.data.categories.datasource.remote.CategoryRemoteDataSource
import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import app.fit.fitndflow.data.common.database.mapper.toModel
import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource
import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.mapper.CategoryModelMapperKt.Companion.toModel
import app.fit.fitndflow.data.common.mapper.convertToStringInLanguages
import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.StringInLanguagesModel

class CategoriesRepositoryImpl(
    private val mContext: Context,
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryDao: CategoryDao,
    private val categoriesAndExercisesLocalDataSource: CategoriesAndExercisesLocalDataSource,
    private val trainingLocalDataSource: TrainingLocalDataSource
) : CategoriesRepository {
    private val isLocalMode = true

    override suspend fun getCategoryList(): List<CategoryModel> {
        if (categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache() == null) {
            val response: List<CategoryModel>
            try {
                if (isLocalMode) {
                    response = categoryDao.getAllCategories().map { it.toModel() }
                } else {
                    response = categoryRemoteDataSource.getCategoryList()
                }
                categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(response)
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(e)
            }
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache().orEmpty()
    }

    override suspend fun addNewCategory(
        categoryName: String,
        language: String
    ): List<CategoryModel> {
        val stringInLanguages = convertToStringInLanguages(language, categoryName)
        val response: List<CategoryModel>
        try {
            if (isLocalMode) {
                categoryDao.insertAllCategories(
                    listOf(
                        CategoryEntity(
                            nameEs = stringInLanguages.spanish.orEmpty(),
                            nameEn = stringInLanguages.english.orEmpty()
                        )
                    )
                )
                response = categoryDao.getAllCategories().map { it.toModel() }
            } else {
                response = toModel(categoryRemoteDataSource.addNewCategory(stringInLanguages))
            }
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(response)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache().orEmpty()
    }

    override suspend fun modifyCategory(
        categoryName: String,
        language: String,
        categoryId: Int,
        imageUrl: String?
    ): List<CategoryModel> {
        val stringInLanguages = convertToStringInLanguages(language, categoryName)
        val response: List<CategoryModel>
        try {
            if (isLocalMode) {
                categoryDao.updateCategory(
                    categoryId = categoryId,
                    nameEs = stringInLanguages.spanish.orEmpty(),
                    nameEn = stringInLanguages.english.orEmpty(),
                )
                response = categoryDao.getAllCategories().map { it.toModel() }
            } else {
                response = toModel(
                    categoryRemoteDataSource.modifyCategory(
                        stringInLanguages,
                        categoryId,
                        imageUrl
                    )
                )
            }
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(response)
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache().orEmpty()
    }

    override suspend fun deleteCategory(categoryId: Int): List<CategoryModel> {
        val response: List<CategoryModel>
        try {
            if (isLocalMode) {
                categoryDao.deleteCategory(categoryId)
                response = categoryDao.getAllCategories().map { it.toModel() }
            } else {
                response = toModel(categoryRemoteDataSource.deleteCategory(categoryId))
            }
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(response)
            trainingLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache().orEmpty()
    }
}
