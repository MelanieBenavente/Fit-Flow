package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.CategoryModel;

public interface CategoriesRepository extends CommonRepository {
    List<CategoryModel> getCategoryList() throws Exception;
    List<CategoryModel> addNewCategory(String categoryName, String language) throws Exception;
    List<CategoryModel> modifyCategory(String categoryName, String language, int categoryId, String imageUrl) throws Exception;
    List<CategoryModel> deleteCategory(Integer integer) throws Exception;
}
