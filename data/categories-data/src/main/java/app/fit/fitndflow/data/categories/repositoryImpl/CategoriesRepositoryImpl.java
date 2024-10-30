package app.fit.fitndflow.data.categories.repositoryImpl;

import android.content.Context;


import com.fit.fitndflow.app.domain.categories.repository.CategoriesRepository;
import com.fit.fitndflow.app.domain.common.models.CategoryModel;

import java.util.List;

import app.fit.fitndflow.data.categories.dto.AddCategoryDto;
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
    private Context mContext;
    private CategoriesApiInterface apiInterface;
    public CategoriesRepositoryImpl(Context context, CategoriesApiInterface apiInterface, CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource, TrainingLocalDataSource trainingLocalDataSource) {
        this.mContext = context;
        this.apiInterface = apiInterface;
        this.categoriesAndExercisesLocalDataSource = categoriesAndExercisesLocalDataSource;
        this.trainingLocalDataSource = trainingLocalDataSource;
    }

    @Override
    public List<CategoryModel> getCategoryList() throws Exception {
        if (categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache() == null) {
            Response<List<CategoryDto>> response;
            try {
                response = apiInterface.getCategoryDtoList().execute();
                if (response != null && !response.isSuccessful()) {
                    throw new ExcepcionApi(response.code());
                }
                if (response != null && response.body() != null) {
                    categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(CategoryModelMapperKt.toModel(response.body()));
                } else {
                    return null;
                }
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
        AddCategoryDto addCategoryDto = new AddCategoryDto(stringInLanguages);

        try {

            Response<List<CategoryDto>> response = apiInterface.addNewCategory(addCategoryDto).execute();

            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(CategoryModelMapperKt.toModel(response.body()));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache();
    }
    @Override
    public List<CategoryModel> modifyCategory(String categoryName, String language, int categoryId, String imageUrl) throws Exception {
        StringInLanguagesDto stringInLanguages = StringInLanguagesMapperKt.convertToStringInLanguages(language, categoryName);
        ModifyCategoryDto modifyCategoryDto = new ModifyCategoryDto(categoryId, stringInLanguages, "");

        try {
            Response<List<CategoryDto>> response = apiInterface.modifyCategory(modifyCategoryDto).execute();

            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(CategoryModelMapperKt.toModel(response.body()));
                trainingLocalDataSource.cleanCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache();
    }

    @Override
    public List<CategoryModel> deleteCategory(Integer categoryId) throws Exception {

        try {
            Response<List<CategoryDto>> response = apiInterface.deleteCategory(categoryId).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                categoriesAndExercisesLocalDataSource.replaceAllDataFromCategoryListCache(CategoryModelMapperKt.toModel(response.body()));
                trainingLocalDataSource.cleanCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return categoriesAndExercisesLocalDataSource.getAvailableCategoryListCache();
    }

}
