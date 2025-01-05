package app.fit.fitndflow.data.categories.datasource.remote

import app.fit.fitndflow.data.categories.datasource.CategoryDataSourceInterface
import app.fit.fitndflow.data.categories.dto.AddCategoryDto
import app.fit.fitndflow.data.categories.dto.ModifyCategoryDto
import app.fit.fitndflow.data.categories.model.CategoriesApiInterface
import app.fit.fitndflow.data.common.dto.CategoryDto
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto
import app.fit.fitndflow.data.common.model.ExcepcionApi

class CategoryRemoteDataSource(private val categoryApiInterface: CategoriesApiInterface) : CategoryDataSourceInterface {
    override fun getCategoryList(): List<CategoryDto> {
        val response = categoryApiInterface.getCategoryDtoList().execute();
        if (response != null && !response.isSuccessful()) {
            throw ExcepcionApi(response.code())
        }
        return response.body()?.toList().orEmpty()
    }

    override fun addNewCategory(categoryName: StringInLanguagesDto): List<CategoryDto> {
        val addCategoryDto = AddCategoryDto(categoryName)
        val response = categoryApiInterface.addNewCategory(addCategoryDto).execute()
        if (response != null && !response.isSuccessful()) {
            throw ExcepcionApi(response.code())
        }
        return response.body()?.toList().orEmpty()
    }

    override fun modifyCategory(
        categoryName: StringInLanguagesDto,
        categoryId: Int,
        imageUrl: String
    ): List<CategoryDto> {
        val modifyCategoryDto = ModifyCategoryDto(categoryId, categoryName, "")
        val response = categoryApiInterface.modifyCategory(modifyCategoryDto).execute()
        if (response != null && !response.isSuccessful()) {
            throw ExcepcionApi(response.code())
        }
        return response.body()?.toList().orEmpty()
    }

    override fun deleteCategory(categoryId: Int): List<CategoryDto> {
        val response = categoryApiInterface.deleteCategory(categoryId).execute()
        if (response != null && !response.isSuccessful()) {
            throw ExcepcionApi(response.code())
        }
        return response.body()?.toList().orEmpty()
    }
}