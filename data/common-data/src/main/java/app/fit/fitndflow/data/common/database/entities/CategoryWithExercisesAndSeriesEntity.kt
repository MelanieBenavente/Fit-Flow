package app.fit.fitndflow.data.common.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithExercisesAndSeriesEntity (
    @Embedded val category: CategoryEntity,
    @Relation(
        entity = ExerciseEntity::class,
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val exercises: List<ExerciseWithSeriesEntity>
)

fun filterTrainingByDate(
    training: List<CategoryWithExercisesAndSeriesEntity>,
    date: String
): List<CategoryWithExercisesAndSeriesEntity> {

    return training.mapNotNull { category ->

        val filteredExercises = category.exercises.mapNotNull { exercise ->

            val filteredSeries = exercise.series.filter { it.date == date }

            if (filteredSeries.isEmpty()) null
            else exercise.copy(series = filteredSeries)
        }

        if (filteredExercises.isEmpty()) null
        else category.copy(exercises = filteredExercises)
    }
}