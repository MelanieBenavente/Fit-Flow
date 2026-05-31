package app.fit.fitndflow.data.common.database.entities

data class ExerciseSerieFlat(
    val exerciseId: Int,
    val exerciseNameEn: String,
    val exerciseNameEs: String,
    val firstReps: Int,
    val firstWeight: Double,
    val reps: Int?,
    val weight: Double?,
    val date: String
)
