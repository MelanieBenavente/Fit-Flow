package app.fit.fitndflow.data.categories.dto

import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddCategoryDto(@SerializedName("name") var name: StringInLanguagesDto?):Serializable
