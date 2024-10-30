package app.fit.fitndflow.data.trainings.dto

import app.fit.fitndflow.data.common.dto.SerieDto
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddSerieResponseDto(
    @SerializedName("id") var exerciseid: Int?,
    @SerializedName("name") var exerciseName: StringInLanguagesDto,
    @SerializedName("serieList") var serieListDto: List<SerieDto>) : Serializable

