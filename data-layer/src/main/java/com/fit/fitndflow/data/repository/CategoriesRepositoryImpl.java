package com.fit.fitndflow.data.repository;

import android.content.Context;

import com.fit.fitndflow.data.common.ApiInterface;
import com.fit.fitndflow.data.common.model.ExcepcionApi;
import com.fit.fitndflow.data.datasource.CategoriesAndExercisesLocalDataSource;
import com.fit.fitndflow.data.datasource.TrainingLocalDataSource;
import com.fit.fitndflow.data.dto.StringInLanguagesDto;
import com.fit.fitndflow.data.dto.categories.AddCategoryDto;
import com.fit.fitndflow.data.dto.categories.CategoryDto;
import com.fit.fitndflow.data.dto.categories.ModifyCategoryDto;
import com.fit.fitndflow.data.dto.mapper.CategoryModelMapperKt;
import com.fit.fitndflow.data.dto.mapper.StringInLanguagesMapperKt;

import java.util.List;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.repository.CategoriesRepository;
import retrofit2.Response;

public class CategoriesRepositoryImpl implements CategoriesRepository {
    private CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource;
    private TrainingLocalDataSource trainingLocalDataSource;
    private Context mContext;
    private ApiInterface apiInterface;
    public CategoriesRepositoryImpl(Context context, ApiInterface apiInterface, CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource, TrainingLocalDataSource trainingLocalDataSource) {
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
