package app.fit.fitndflow.data.common.database.mapper

import app.fit.fitndflow.data.common.database.entities.CategoryEntity
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.models.StringInLanguagesModel

fun CategoryEntity.toModel() = CategoryModel(id, StringInLanguagesModel(nameEs, nameEn))
