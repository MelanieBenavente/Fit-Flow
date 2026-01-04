package app.fit.fitndflow.data.common.database.entities

data class ExerciseSerieFlat(
    val exerciseId: Int,
    val exerciseNameEn: String,
    val exerciseNameEs: String,
    val reps: Int?,
    val weight: Double?,
    val date: String
)