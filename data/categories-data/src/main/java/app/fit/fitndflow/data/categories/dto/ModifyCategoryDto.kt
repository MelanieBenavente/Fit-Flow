package app.fit.fitndflow.data.categories.dto

import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModifyCategoryDto(@SerializedName("id") var id: Int?, @SerializedName("name") var name: StringInLanguagesDto?, @SerializedName("imageUrl") var imageUrl: String? = null): Serializable
