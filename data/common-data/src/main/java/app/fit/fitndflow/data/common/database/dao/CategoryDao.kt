package app.fit.fitndflow.data.common.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import app.fit.fitndflow.data.common.database.entities.CategoryWithExercisesEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories_table")
    suspend fun getAllCategories(): List<CategoryWithExercisesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity):Long

    @Query("UPDATE categories_table SET nameEs = :nameEs, nameEn = :nameEn WHERE id = :categoryId")
    suspend fun updateCategory(categoryId: Int, nameEs: String, nameEn: String)

    @Query("DELETE FROM categories_table WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: Int)
}