package app.fit.fitndflow.data.exercises.dto

import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName

data class ModifyExerciseDto(@SerializedName("id") var id: Int?, @SerializedName("name") var name: StringInLanguagesDto?, @SerializedName("primaryCategory") var idCategory: Int?)
