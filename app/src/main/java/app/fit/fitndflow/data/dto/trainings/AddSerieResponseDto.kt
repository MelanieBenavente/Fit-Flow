package app.fit.fitndflow.data.dto.trainings

import app.fit.fitndflow.data.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddSerieResponseDto(@SerializedName("id") var exerciseid: Int?, @SerializedName("name") var exerciseName: StringInLanguagesDto, @SerializedName("serieList") var serieListDto: List<SerieDto>): Serializable

data class SerieDto(@SerializedName("id") var serieId: Int?, @SerializedName("reps") var reps: Int? = null, @SerializedName("weight") var weight: Double? = null): Serializable
