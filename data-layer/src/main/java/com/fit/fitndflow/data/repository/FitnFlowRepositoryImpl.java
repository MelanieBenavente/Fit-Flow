package com.fit.fitndflow.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fit.fitndflow.data.common.ApiInterface;
import com.fit.fitndflow.data.common.SharedPrefs;
import com.fit.fitndflow.data.common.model.ExcepcionApi;
import com.fit.fitndflow.data.dto.StringInLanguagesDto;
import com.fit.fitndflow.data.dto.UserDto;
import com.fit.fitndflow.data.dto.categories.AddCategoryDto;
import com.fit.fitndflow.data.dto.categories.CategoryDto;
import com.fit.fitndflow.data.dto.categories.ModifyCategoryDto;
import com.fit.fitndflow.data.dto.exercises.AddExerciseDto;
import com.fit.fitndflow.data.dto.exercises.ExerciseDto;
import com.fit.fitndflow.data.dto.exercises.ModifyExerciseDto;
import com.fit.fitndflow.data.dto.mapper.StringInLanguagesMapperKt;
import com.fit.fitndflow.data.dto.trainings.AddSerieRequestDto;
import com.fit.fitndflow.data.dto.trainings.SerieDto;
import com.fit.fitndflow.data.dto.trainings.SerieForAddSerieRequestDto;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.model.UserModel;
import com.fit.fitndflow.data.dto.mapper.CategoryModelMapperKt;
import com.fit.fitndflow.data.dto.mapper.ExerciseModelMapperKt;
import com.fit.fitndflow.data.dto.mapper.UserModelMapperKt;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import retrofit2.Response;

public class FitnFlowRepositoryImpl implements FitnFlowRepository {
    private static FitnFlowRepositoryImpl instance;
    private List<CategoryModel> availableCategoryListCachedResponse;
    private HashMap<String, List<CategoryModel>> trainingResponseCacheByDate = new HashMap<>();
    private Context mContext;
    private ApiInterface apiInterface;
    private String currentDate;
    public FitnFlowRepositoryImpl(Context context, ApiInterface apiInterface){
        this.mContext = context;
        this.apiInterface = apiInterface;
    }

    private  void removeAllDataFromHashMapCache(){
        trainingResponseCacheByDate.clear();
    }

    @Override
    public UserModel registerUser(String userName, String email, String premium) throws Exception {
        UserDto userDto = new UserDto(userName, email, premium, null);

        Response<UserDto> response;
        try {
            response = apiInterface.register(userDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code()); //si no ha ido bien la llamada: server no devuelve un 200
            }
            if (response != null && response.body() != null) {
                UserModel userModelMapped = UserModelMapperKt.toModel(response.body());
                SharedPrefs.saveApikeyToSharedPRefs(mContext, userModelMapped.getApiKey());
                return userModelMapped;
            } else {
                throw new Exception("Error register"); //si la respuesta es nula
            }
        } catch (Exception e) {
            //solamente salta cuando hay un error de mapeo (ej. server me devuelve un json diferente al esperado)
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public List<CategoryModel> getCategoryList() throws Exception {
        if (availableCategoryListCachedResponse == null) {
            Response<List<CategoryDto>> response;
            try {
                response = apiInterface.getCategoryDtoList().execute();
                if (response != null && !response.isSuccessful()) {
                    throw new ExcepcionApi(response.code());
                }
                if (response != null && response.body() != null) {
                    availableCategoryListCachedResponse = CategoryModelMapperKt.toModel(response.body());
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
        return availableCategoryListCachedResponse;
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
                availableCategoryListCachedResponse = CategoryModelMapperKt.toModel(response.body());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableCategoryListCachedResponse;
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
                availableCategoryListCachedResponse = CategoryModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableCategoryListCachedResponse;
    }
    @Override
    public List<CategoryModel> deleteCategory(Integer categoryId) throws Exception {

        try {
            Response <List<CategoryDto>> response = apiInterface.deleteCategory(categoryId).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                availableCategoryListCachedResponse = CategoryModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableCategoryListCachedResponse;
    }
   @Override
    public List<ExerciseModel> addNewExercise(String exerciseName, String language, int categoryId) throws Exception {
       StringInLanguagesDto stringInLanguages = StringInLanguagesMapperKt.convertToStringInLanguages(language, exerciseName);
       AddExerciseDto addExerciseDto = new AddExerciseDto(stringInLanguages, categoryId);
       List<ExerciseModel> availableExerciseListResponse;
        try {

            Response<List<ExerciseDto>> response = apiInterface.addNewExercise(addExerciseDto).execute();

            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                availableExerciseListResponse = ExerciseModelMapperKt.toModel(response.body());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableExerciseListResponse;
    }
    @Override
    public List<ExerciseModel> modifyExercise(int exerciseId, String exerciseName, String language, int categoryId) throws Exception {
        StringInLanguagesDto stringInLanguages = StringInLanguagesMapperKt.convertToStringInLanguages(language, exerciseName);
        ModifyExerciseDto modifyExerciseDto = new ModifyExerciseDto(exerciseId, stringInLanguages, categoryId);
        List<ExerciseModel> availableExerciseListResponse;
        try {
            Response <List<ExerciseDto>> response = apiInterface.modifyExercise(modifyExerciseDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                availableExerciseListResponse = ExerciseModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableExerciseListResponse;
    }
    @Override
    public List<ExerciseModel> deleteExercise(Integer exerciseId) throws Exception {
        List<ExerciseModel> availableExerciseListResponse;
        try {
            Response <List<ExerciseDto>> response = apiInterface.deleteExercise(exerciseId).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                availableExerciseListResponse = ExerciseModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableExerciseListResponse;
    }

    public ExerciseModel addNewSerie(int reps, double weight, int exerciseId) throws Exception {
        SerieForAddSerieRequestDto serieForAddSerieRequestDto = new SerieForAddSerieRequestDto(reps, weight, new ExerciseDto(exerciseId, null, null, null, null));
        AddSerieRequestDto addSerieRequestDto = new AddSerieRequestDto(currentDate, serieForAddSerieRequestDto);
        ExerciseModel exerciseResponse;
        try{
            Response <ExerciseDto> response = apiInterface.addNewSerie(addSerieRequestDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseResponse = ExerciseModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
    }

    @Override
    public List<CategoryModel> getTrainingList(String date) throws Exception {
        currentDate = date;
        if(trainingResponseCacheByDate.get(date) == null){
            try {
                Response<List<CategoryDto>> response = apiInterface.getCategoriesAndTrainings(date).execute();
                if(response != null){
                    trainingResponseCacheByDate.put(date, CategoryModelMapperKt.toModel(response.body()));}

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
        return trainingResponseCacheByDate.get(date);
    }

    @Override
    public List<CategoryModel> updateCurrentTrainingListCache() throws Exception {
        return getTrainingList(currentDate);
    }

    @Override
    public ExerciseModel modifySerie(int serieId, int reps, double weight) throws Exception{
        SerieDto serieDto = new SerieDto(serieId, reps, weight);
        ExerciseModel exerciseResponse;
        try{
            Response<ExerciseDto> response = apiInterface.modifySerie(serieDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseResponse = ExerciseModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
    }

    public List<SerieModel> getSerieListOfExerciseAdded(int exerciseId) throws Exception{
        try{
            List<CategoryModel> categoryList = trainingResponseCacheByDate.get(currentDate);
            if(categoryList != null) {
                for (int i = 0; i < categoryList.size(); i++) {
                    CategoryModel category = categoryList.get(i);
                    List<ExerciseModel> exerciseList = category.getExerciseList();
                    for (int j = 0; j < exerciseList.size(); j++) {
                        ExerciseModel exercise = exerciseList.get(j);
                        if (exercise.getId() == exerciseId) {
                            return exercise.getSerieList();
                        }
                    }
                }
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    public ExerciseModel deleteSerie(int serieId) throws Exception{
        ExerciseModel exerciseResponse;
        try{
           Response<ExerciseDto> response = apiInterface.deleteSerie(serieId).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseResponse = ExerciseModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
        }
    }

