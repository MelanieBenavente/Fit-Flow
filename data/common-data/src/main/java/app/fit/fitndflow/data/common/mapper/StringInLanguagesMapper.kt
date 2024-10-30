package app.fit.fitndflow.data.common.mapper

import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import app.fit.fitndflow.domain.Utils
import app.fit.fitndflow.domain.model.StringInLanguagesModel

fun StringInLanguagesDto?.toModel() = this?.let { StringInLanguagesModel(this.spanish?:"", this.english?:"") } ?: StringInLanguagesModel("", "")

fun convertToStringInLanguages(language: String, nameCategory: String?): StringInLanguagesDto {
    val stringInLanguagesDto = if (language == Utils.SPANISH) {
        StringInLanguagesDto(nameCategory, "")
    } else {
        StringInLanguagesDto("", nameCategory)
    }
    return stringInLanguagesDto
}