package app.fit.fitndflow.data.common.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithExercisesEntity (
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val exercises: List<ExerciseEntity>
)