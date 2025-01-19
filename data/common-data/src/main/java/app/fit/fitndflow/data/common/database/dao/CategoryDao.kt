package app.fit.fitndflow.data.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.fit.fitndflow.data.common.database.entities.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories_table")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<CategoryEntity>)

    //todo modifycategory y deletecategoru
}