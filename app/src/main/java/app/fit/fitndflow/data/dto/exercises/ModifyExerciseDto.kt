package app.fit.fitndflow.data.dto.exercises

import app.fit.fitndflow.data.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName

data class ModifyExerciseDto(@SerializedName("id") var id: Int?, @SerializedName("name") var name: StringInLanguagesDto?,@SerializedName("primaryCategory") var idCategory: Int?)
