package app.fit.fitndflow.data.exercises.dto

import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddExerciseDto(@SerializedName("name") var name: StringInLanguagesDto?, @SerializedName("primaryCategory") var idCategory: Int?): Serializable
