package app.fit.fitndflow.data.categories.repositoryImpl;

import android.content.Context;


import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository;
import com.fit.fitndflow.app.domain.common.models.CategoryModel;

import java.util.List;

import app.fit.fitndflow.data.categories.datasource.remote.CategoryRemoteDataSource;
import app.fit.fitndflow.data.categories.dto.AddCategoryDto;
import app.fit.fitndflow.data.common.database.dao.CategoryDao;
import app.fit.fitndflow.data.common.dto.CategoryDto;
import app.fit.fitndflow.data.categories.dto.ModifyCategoryDto;
import app.fit.fitndflow.data.categories.model.CategoriesApiInterface;
import app.fit.fitndflow.data.common.mapper.StringInLanguagesMapperKt;
import app.fit.fitndflow.data.common.model.ExcepcionApi;
import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource;
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource;
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.common.mapper.CategoryModelMapperKt;
import retrofit2.Response;

public class CategoriesRepositoryImpl implements CategoriesRepository {
    private CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource;
    private TrainingLocalDataSource trainingLocalDataSource;
    private CategoryRemoteDataSource categoryRemoteDataSource;
    private CategoryDao categoryDao;
    private Context mContext;
    private boolean isLocalMode = true;

    public CategoriesRepositoryImpl(Context context, CategoryRemoteDataSource categoryRemoteDataSource, CategoryDao categoryDao ,CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource, TrainingLocalDataSource trainingLocalDataSource) {
        this.mContext = context;
        this.categoryRemoteDataSource = categoryRemoteDataSource;
        this.categoriesAndExercisesLocalDataSource = categoriesAndExercisesLocalDataSource;
        this.trainingLocalDataSource = trainingLocalDataSource;
        this.categoryDao = categoryDao;
    }

    @Override
    public List<CategoryModel> getCategoryList() throws Exception {
        if (categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache() == null) {
            List<CategoryModel> response;
            try {
                if (isLocalMode) {
                    response = categoryDao.getAllCategories();
                } else {
                    response = categoryRemoteDataSource.getCategoryList();
                }
                categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(response);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache();
    }

    @Override
    public List<CategoryModel> addNewCategory(String categoryName, String language) throws Exception {
        StringInLanguagesDto stringInLanguages = StringInLanguagesMapperKt.convertToStringInLanguages(language, categoryName);
        List<CategoryDto> response;
        try {
            response = categoryRemoteDataSource.addNewCategory(stringInLanguages);
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(CategoryModelMapperKt.toModel(response));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache();
    }

    @Override
    public List<CategoryModel> modifyCategory(String categoryName, String language, int categoryId, String imageUrl) throws Exception {
        StringInLanguagesDto stringInLanguages = StringInLanguagesMapperKt.convertToStringInLanguages(language, categoryName);
        List<CategoryDto> response;
        try {
            response = categoryRemoteDataSource.modifyCategory(stringInLanguages, categoryId, imageUrl);
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(CategoryModelMapperKt.toModel(response));
            trainingLocalDataSource.cleanCache();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache();
    }

    @Override
    public List<CategoryModel> deleteCategory(Integer categoryId) throws Exception {
        List<CategoryDto> response;
        try {
            response = categoryRemoteDataSource.deleteCategory(categoryId);
            categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(CategoryModelMapperKt.toModel(response));
            trainingLocalDataSource.cleanCache();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache();
    }
}
