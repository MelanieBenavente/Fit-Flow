package app.fit.fitndflow.data.common.database.entities

import androidx.room.Embedded

data class ExerciseWithSeriesEntity (
    @Embedded val exercise: ExerciseEntity,
    val series: List<SerieEntity>
)