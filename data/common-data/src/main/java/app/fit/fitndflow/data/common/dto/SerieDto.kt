package app.fit.fitndflow.data.common.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SerieDto(
    @SerializedName("id") var serieId: Int?,
    @SerializedName("reps") var reps: Int? = null,
    @SerializedName("weight") var weight: Double? = null) : Serializable
