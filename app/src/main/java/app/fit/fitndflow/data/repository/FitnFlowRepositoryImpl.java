package app.fit.fitndflow.data.repository;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.common.RetrofitUtils;
import app.fit.fitndflow.data.common.model.ExcepcionApi;
import app.fit.fitndflow.data.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.AddCategoryDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ModifyCategoryDto;
import app.fit.fitndflow.data.dto.exercises.AddExerciseDto;
import app.fit.fitndflow.data.dto.exercises.ExerciseDto;
import app.fit.fitndflow.data.dto.exercises.ModifyExerciseDto;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.CategoryModelInLanguages;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.ExerciseModelInLanguages;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.model.mapper.CategoryModelMapperKt;
import app.fit.fitndflow.domain.model.mapper.ExerciseModelMapperKt;
import app.fit.fitndflow.domain.model.mapper.UserModelMapperKt;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import retrofit2.Response;

public class FitnFlowRepositoryImpl implements FitnFlowRepository {
    private List<CategoryModel> categoryListCachedResponse;
    private List<ExerciseModel> exerciseListCachedResponse;

    @Override
    public UserModel registerUser(UserDto userDto) throws Exception {
        Response<UserDto> response;
        try {
            response = RetrofitUtils.getRetrofitUtils().register(userDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                return UserModelMapperKt.toModel(response.body());
            } else {
                throw new Exception("Error register");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public List<CategoryModel> getCategoryList(String apiKey) throws Exception {
        if (categoryListCachedResponse == null) {
            Response<List<CategoryDto>> response;
            try {
                response = RetrofitUtils.getRetrofitUtils().getCategoryDtoList(apiKey).execute();
                if (response != null && !response.isSuccessful()) {
                    throw new ExcepcionApi(response.code());
                }
                if (response != null && response.body() != null) {
                    categoryListCachedResponse = CategoryModelMapperKt.toModel(response.body());
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        return categoryListCachedResponse;
    }

    @Override
    public Boolean saveCategoryAndExercises(CategoryDto categoryDto, String apikey) throws Exception {
        try {
            Response<CategoryDto> response = RetrofitUtils.getRetrofitUtils().saveCategory(categoryDto, apikey).execute();
            if (response.isSuccessful()) {
                deleteCache();
                return true;
            } else {
                throw new Exception(new Exception("Error from Server"));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void deleteCache() {
        categoryListCachedResponse = null;
    }


    private List<CategoryModel> mockList() {

        List<CategoryModel> categoryTrainingModelList = new ArrayList<>();
        List<SerieModel> serieModelList = new ArrayList<>();
        SerieModel serie1 = new SerieModel(1, 12, 10.5);
        SerieModel serie2 = new SerieModel(2, 8, 5.5);
        SerieModel serie3 = new SerieModel(3, 14, 2.5);
        serieModelList.add(serie1);
        serieModelList.add(serie2);
        serieModelList.add(serie3);

        List<ExerciseModel> exerciseList = new ArrayList<>();
        ExerciseModel exercise1 = new ExerciseModel(221, "Mancuernas", serieModelList);
        exerciseList.add(exercise1);

        CategoryModel category1 = new CategoryModel(123, "toto", exerciseList);
        CategoryModel category2 = new CategoryModel(124, "Pecho", exerciseList);
        CategoryModel category3 = new CategoryModel(125, "Triceps", exerciseList);

        categoryTrainingModelList.add(category1);
        categoryTrainingModelList.add(category2);
        categoryTrainingModelList.add(category3);

        return categoryTrainingModelList;

    }

    private List<CategoryModel> mockEmptyList() {
        return new ArrayList<CategoryModel>();
    }

    public List<CategoryModel> getTrainingList(String apiKey) throws Exception {
        return mockEmptyList();
        //todo if i want a mocked list: return mockList();
    }

    @Override
    public List<CategoryModel> addNewCategory(StringInLanguagesDto categoryName, String apiKey) throws Exception {
        try {

            AddCategoryDto addCategoryDto = new AddCategoryDto(categoryName);
            Response<List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().addNewCategory(addCategoryDto, apiKey).execute();

            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                categoryListCachedResponse = CategoryModelMapperKt.toModel(response.body());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return categoryListCachedResponse;
    }

    @Override
    public List<CategoryModel> modifyCategory(CategoryModelInLanguages category, String apiKey) throws Exception {
        try {
            ModifyCategoryDto modifyCategoryDto = new ModifyCategoryDto(category.getId(), category.getName(), category.getImageUrl());
            Response<List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().modifyCategory(modifyCategoryDto, apiKey).execute();

            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                categoryListCachedResponse = CategoryModelMapperKt.toModel(response.body());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return categoryListCachedResponse;
    }
    @Override
    public List<CategoryModel> deleteCategory(Integer categoryId, String apikey) throws Exception {
        try {
            Response <List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().deleteCategory(categoryId, apikey).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                categoryListCachedResponse = CategoryModelMapperKt.toModel(response.body());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return categoryListCachedResponse;
    }
   @Override
    public List<ExerciseModel> addNewExercise(StringInLanguagesDto exerciseName, int categoryId, String apiKey) throws Exception {
        try {

            AddExerciseDto addExerciseDto = new AddExerciseDto(exerciseName, categoryId);
            Response<List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().addNewExercise(addExerciseDto, apiKey).execute();

            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseListCachedResponse = ExerciseModelMapperKt.toModel(response.body());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return exerciseListCachedResponse;
    }
    @Override
    public List<ExerciseModel> modifyExercise(ExerciseModelInLanguages exercise, String apiKey) throws Exception {
        try {
            ModifyExerciseDto modifyExerciseDto = new ModifyExerciseDto(exercise.getId(), exercise.getName(), exercise.getCategoryId());
            Response <List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().modifyExercise(modifyExerciseDto, apiKey).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseListCachedResponse = ExerciseModelMapperKt.toModel(response.body());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return exerciseListCachedResponse;
    }
    @Override
    public List<ExerciseModel> deleteExercise(Integer exerciseId, String apikey) throws Exception {
        try {
            Response <List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().deleteExercise(exerciseId, apikey).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseListCachedResponse = ExerciseModelMapperKt.toModel(response.body());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return exerciseListCachedResponse;
    }
}
