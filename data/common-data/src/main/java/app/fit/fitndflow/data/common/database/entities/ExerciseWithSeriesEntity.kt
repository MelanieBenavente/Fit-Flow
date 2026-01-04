package app.fit.fitndflow.data.common.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseWithSeriesEntity (
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val series: List<SerieEntity>
)