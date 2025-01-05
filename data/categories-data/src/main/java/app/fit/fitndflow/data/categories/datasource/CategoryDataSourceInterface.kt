package app.fit.fitndflow.data.categories.datasource

import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto

interface CategoryDataSourceInterface {

    fun getCategoryList() : List<CategoryDto>

    fun addNewCategory(categoryName: StringInLanguagesDto) : List<CategoryDto>

    fun modifyCategory(categoryName: StringInLanguagesDto, categoryId: Int, imageUrl: String) : List<CategoryDto>

    fun deleteCategory(categoryId: Int) : List<CategoryDto>
}