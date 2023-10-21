package app.fit.fitndflow.data.dto.categories

import app.fit.fitndflow.data.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryDto(@SerializedName("id") var id: Int?, @SerializedName("name") var name: StringInLanguagesDto?, @SerializedName("exercises") var exerciseDtoList: List<ExerciseDto>?): Serializable
