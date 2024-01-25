package app.fit.fitndflow.data.dto.trainings

import app.fit.fitndflow.data.dto.exercises.ExerciseDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddSerieRequestDto(@SerializedName("date") var date: String, @SerializedName("serie") var serieForAdd: SerieForAddSerieRequestDto?): Serializable
data class SerieForAddSerieRequestDto(@SerializedName("reps") var reps: Int? = null, @SerializedName("weight") var weight: Double? = null, @SerializedName("exercise") var exercise: ExerciseDto): Serializable