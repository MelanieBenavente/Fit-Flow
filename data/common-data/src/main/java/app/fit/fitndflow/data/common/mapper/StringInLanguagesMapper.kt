package app.fit.fitndflow.data.common.mapper

import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import com.fit.fitndflow.app.domain.common.models.StringInLanguagesModel
import com.fit.fitndflow.app.domain.common.utils.Utils


fun StringInLanguagesDto?.toModel() = this?.let { StringInLanguagesModel(this.spanish?:"", this.english?:"") } ?: StringInLanguagesModel("", "")

fun convertToStringInLanguages(language: String, nameCategory: String?): StringInLanguagesDto {
    val stringInLanguagesDto = if (language == Utils.SPANISH) {
        StringInLanguagesDto(nameCategory, "")
    } else {
        StringInLanguagesDto("", nameCategory)
    }
    return stringInLanguagesDto
}