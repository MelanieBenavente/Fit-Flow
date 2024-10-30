package com.fit.fitndflow.app.domain.categories.repository;

import com.fit.fitndflow.app.domain.common.models.CategoryModel;
import com.fit.fitndflow.app.domain.common.repository.CommonRepository;
import java.util.List;

public interface CategoriesRepository extends CommonRepository {
    List<CategoryModel> getCategoryList() throws Exception;
    List<CategoryModel> addNewCategory(String categoryName, String language) throws Exception;
    List<CategoryModel> modifyCategory(String categoryName, String language, int categoryId, String imageUrl) throws Exception;
    List<CategoryModel> deleteCategory(Integer integer) throws Exception;
}
