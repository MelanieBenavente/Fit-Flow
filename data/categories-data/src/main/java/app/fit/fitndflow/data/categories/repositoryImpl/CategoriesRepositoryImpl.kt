package app.fit.fitndflow.data.categories.repositoryImpl

import app.fit.fitndflow.data.categories.datasource.local.InitialExercisesCreatorHelper
import app.fit.fitndflow.data.categories.datasource.remote.CategoryRemoteDataSource
import app.fit.fitndflow.data.common.database.DataBase
import app.fit.fitndflow.data.common.database.dao.CategoryDao
import app.fit.fitndflow.data.common.database.dao.ExerciseDao
import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import app.fit.fitndflow.data.common.database.entities.ExerciseEntity
import app.fit.fitndflow.data.common.database.mapper.toModel
import app.fit.fitndflow.data.common.datasource.local.CategoriesAndExercisesCacheLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.SharedPrefsLocalDataSource
import app.fit.fitndflow.data.common.datasource.local.TrainingCacheLocalDataSource
import app.fit.fitndflow.data.common.mapper.CategoryModelMapperKt.Companion.toModel
import app.fit.fitndflow.data.common.mapper.convertToStringInLanguages
import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository
import com.fit.fitndflow.app.domain.common.models.CategoryModel

class CategoriesRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryDao: CategoryDao,
    private val exerciseDao: ExerciseDao,
    private val categoriesAndExercisesCacheLocalDataSource: CategoriesAndExercisesCacheLocalDataSource,
    private val trainingCacheLocalDataSource: TrainingCacheLocalDataSource,
    private val initialExercisesCreatorHelper: InitialExercisesCreatorHelper,
    private val sharedPrefsLocalDataSource: SharedPrefsLocalDataSource,
) : CategoriesRepository {
    private val isLocalMode
        get() = sharedPrefsLocalDataSource.getIsLocal()

    override suspend fun getCategoryList(): List<CategoryModel> {
        if (categoriesAndExercisesCacheLocalDataSource.getAvailableCategoryListCache() == null) {
            val response: List<CategoryModel>
            try {
                if (isLocalMode) {
                    response = categoryDao.getAllCategories().map { it.toModel() }
                } else {
                    response = categoryRemoteDataSource.getCategoryList()
                }
                categoriesAndExercisesCacheLocalDataSource.replaceAllDataFromCategoryListCache(
                    response
                )
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(e)
            }
        }
        return categoriesAndExercisesCacheLocalDataSource.getAvailableCategoryListCache().orEmpty()
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
            categoriesAndExercisesCacheLocalDataSource.replaceAllDataFromCategoryListCache(response)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesCacheLocalDataSource.getAvailableCategoryListCache().orEmpty()
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
            categoriesAndExercisesCacheLocalDataSource.replaceAllDataFromCategoryListCache(response)
            trainingCacheLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesCacheLocalDataSource.getAvailableCategoryListCache().orEmpty()
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
            categoriesAndExercisesCacheLocalDataSource.replaceAllDataFromCategoryListCache(response)
            trainingCacheLocalDataSource.cleanCache()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
        return categoriesAndExercisesCacheLocalDataSource.getAvailableCategoryListCache().orEmpty()
    }

    override suspend fun migrateToLocal() {
        val remoteCategories = categoryRemoteDataSource.getCategoryList()

        val categoryEntities = remoteCategories.mapNotNull { category ->
            category.id?.let { id ->
                CategoryEntity(
                    id = id,
                    nameEs = category.name.spanish,
                    nameEn = category.name.english
                )
            }
        }

        val exerciseEntities = remoteCategories.flatMap { category ->
            val categoryId = category.id ?: return@flatMap emptyList()
            category.exerciseList.orEmpty().mapNotNull { exercise ->
                exercise.id?.let { exerciseId ->
                    ExerciseEntity(
                        id = exerciseId,
                        categoryId = categoryId,
                        nameEs = exercise.name.spanish,
                        nameEn = exercise.name.english,
                        recordHeight = exercise.record?.kg ?: 0.0,
                        recordReps = exercise.record?.reps ?: 0,
                        firstReps = exercise.lastFirstSerie?.reps ?: 0,
                        firstWeight = exercise.lastFirstSerie?.kg ?: 0.0
                    )
                }
            }
        }
        categoryDao.insertAllCategories(categoryEntities)
        exerciseDao.insertExercises(exerciseEntities)

        categoriesAndExercisesCacheLocalDataSource.cleanCache()
        trainingCacheLocalDataSource.cleanCache()
        sharedPrefsLocalDataSource.saveIsLocal()
        sharedPrefsLocalDataSource.saveInitialDataCreated()
        sharedPrefsLocalDataSource.saveApiKey(null)
    }

    override suspend fun createInitialData() {
        sharedPrefsLocalDataSource.saveIsLocal()
        initialExercisesCreatorHelper.insertInitialCategories()
        sharedPrefsLocalDataSource.saveInitialDataCreated()
        return
    }

    override fun isInitialDataCreated() = sharedPrefsLocalDataSource.getIsInitialDataCreated()
}
