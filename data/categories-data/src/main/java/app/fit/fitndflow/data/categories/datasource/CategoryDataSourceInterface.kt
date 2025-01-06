package app.fit.fitndflow.data.categories.datasource

import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import com.fit.fitndflow.app.domain.common.models.CategoryModel

interface CategoryDataSourceInterface {

    fun getCategoryList() : List<CategoryModel>

    fun addNewCategory(categoryName: StringInLanguagesDto) : List<CategoryDto>

    fun modifyCategory(categoryName: StringInLanguagesDto, categoryId: Int, imageUrl: String) : List<CategoryDto>

    fun deleteCategory(categoryId: Int) : List<CategoryDto>
}