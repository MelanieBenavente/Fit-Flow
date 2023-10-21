package app.fit.fitndflow.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StringInLanguagesDto(@SerializedName("es") var spanish: String?, @SerializedName("en") val english: String?): Serializable
