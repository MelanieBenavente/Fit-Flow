package app.fit.fitndflow.data.dto.exercises

import app.fit.fitndflow.data.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddExerciseDto(@SerializedName("name") var name: StringInLanguagesDto?, @SerializedName("primaryCategory") var idCategory: Int?): Serializable
