package app.fit.fitndflow.domain.model.mapper

import app.fit.fitndflow.data.dto.StringInLanguagesDto
import app.fit.fitndflow.domain.model.StringInLanguagesModel

fun StringInLanguagesDto?.toModel() = this?.let { StringInLanguagesModel(this.spanish?:"", this.english?:"") } ?: StringInLanguagesModel("", "")