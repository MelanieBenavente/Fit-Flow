package app.fit.fitndflow.data.dto.categories

import app.fit.fitndflow.data.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExerciseDto(@SerializedName("id") var id: Int?, @SerializedName("name") var exerciseName: StringInLanguagesDto?): Serializable
