package com.fit.fitndflow.data.dto.mapper

import app.fit.fitndflow.domain.Utils
import app.fit.fitndflow.domain.model.StringInLanguagesModel
import com.fit.fitndflow.data.dto.StringInLanguagesDto

fun StringInLanguagesDto?.toModel() = this?.let { StringInLanguagesModel(this.spanish?:"", this.english?:"") } ?: StringInLanguagesModel("", "")

fun convertToStringInLanguages(language: String, nameCategory: String?): StringInLanguagesDto {
    val stringInLanguagesDto = if (language == Utils.SPANISH) {
        StringInLanguagesDto(nameCategory, "")
    } else {
        StringInLanguagesDto("", nameCategory)
    }
    return stringInLanguagesDto
}