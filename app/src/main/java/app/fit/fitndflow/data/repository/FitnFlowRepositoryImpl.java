package app.fit.fitndflow.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.fit.fitndflow.data.common.RetrofitUtils;
import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.common.model.ExcepcionApi;
import app.fit.fitndflow.data.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.AddCategoryDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ModifyCategoryDto;
import app.fit.fitndflow.data.dto.exercises.AddExerciseDto;
import app.fit.fitndflow.data.dto.exercises.ExerciseDto;
import app.fit.fitndflow.data.dto.exercises.ModifyExerciseDto;
import app.fit.fitndflow.data.dto.trainings.AddSerieRequestDto;
import app.fit.fitndflow.data.dto.trainings.AddSerieResponseDto;
import app.fit.fitndflow.data.dto.trainings.SerieDto;
import app.fit.fitndflow.data.dto.trainings.SerieForAddSerieRequestDto;
import app.fit.fitndflow.domain.Utils;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.model.mapper.CategoryModelMapperKt;
import app.fit.fitndflow.domain.model.mapper.ExerciseModelMapperKt;
import app.fit.fitndflow.domain.model.mapper.SerieModelMapperKt;
import app.fit.fitndflow.domain.model.mapper.UserModelMapperKt;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import retrofit2.Response;

public class FitnFlowRepositoryImpl implements FitnFlowRepository {
    private static FitnFlowRepositoryImpl instance;
    private List<CategoryModel> availableCategoryListCachedResponse;
    private HashMap<String, List<CategoryModel>> trainingResponseCacheByDate = new HashMap<>();
    private Context mContext;
    private String currentDate;
    public FitnFlowRepositoryImpl(Context context){
        this.mContext = context;
    }

    private  void removeAllDataFromHashMapCache(){
        trainingResponseCacheByDate.clear();
    }

    private String getApiKey() {
        return SharedPrefs.getApikeyFromSharedPRefs(mContext); }

    @Override
    public UserModel registerUser(String userName, String email, String premium) throws Exception {
        UserDto userDto = new UserDto(userName, email, premium, getApiKey());

        Response<UserDto> response;
        try {
            response = RetrofitUtils.getRetrofitUtils().register(userDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                UserModel userModelMapped = UserModelMapperKt.toModel(response.body());
                SharedPrefs.saveApikeyToSharedPRefs(mContext, userModelMapped.getApiKey());
                return userModelMapped;
            } else {
                throw new Exception("Error register");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public List<CategoryModel> getCategoryList() throws Exception {
        if (availableCategoryListCachedResponse == null) {
            Response<List<CategoryDto>> response;
            try {
                response = RetrofitUtils.getRetrofitUtils().getCategoryDtoList(getApiKey()).execute();
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
        StringInLanguagesDto stringInLanguages = Utils.convertToStringInLanguages(language, categoryName);
        AddCategoryDto addCategoryDto = new AddCategoryDto(stringInLanguages);

        try {

            Response<List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().addNewCategory(addCategoryDto, getApiKey()).execute();

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
        StringInLanguagesDto stringInLanguages = Utils.convertToStringInLanguages(language, categoryName);
        ModifyCategoryDto modifyCategoryDto = new ModifyCategoryDto(categoryId, stringInLanguages, "");

        try {
            Response<List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().modifyCategory(modifyCategoryDto, getApiKey()).execute();

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
            Response <List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().deleteCategory(categoryId, getApiKey()).execute();
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
       StringInLanguagesDto stringInLanguages = Utils.convertToStringInLanguages(language, exerciseName);
       AddExerciseDto addExerciseDto = new AddExerciseDto(stringInLanguages, categoryId);
       List<ExerciseModel> availableExerciseListResponse;
        try {

            Response<List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().addNewExercise(addExerciseDto, getApiKey()).execute();

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
        StringInLanguagesDto stringInLanguages = Utils.convertToStringInLanguages(language, exerciseName);
        ModifyExerciseDto modifyExerciseDto = new ModifyExerciseDto(exerciseId, stringInLanguages, categoryId);
        List<ExerciseModel> availableExerciseListResponse;
        try {
            Response <List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().modifyExercise(modifyExerciseDto, getApiKey()).execute();
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
            Response <List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().deleteExercise(exerciseId, getApiKey()).execute();
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

    public List<SerieModel> addNewSerie(int reps, double weight, int exerciseId) throws Exception {
        SerieForAddSerieRequestDto serieForAddSerieRequestDto = new SerieForAddSerieRequestDto(reps, weight, new ExerciseDto(exerciseId, null, null));
        AddSerieRequestDto addSerieRequestDto = new AddSerieRequestDto(currentDate, serieForAddSerieRequestDto);
        List<SerieModel> serieListResponse;
        try{
            Response <AddSerieResponseDto> response = RetrofitUtils.getRetrofitUtils().addNewSerie(addSerieRequestDto, getApiKey()).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                serieListResponse = SerieModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return serieListResponse;
    }

    @Override
    public List<CategoryModel> getTrainingList(String date) throws Exception {
        currentDate = date;
        if(trainingResponseCacheByDate.get(date) == null){
            try {
                Response<List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().getCategoriesAndTrainings(date, getApiKey()).execute();
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
    public List<SerieModel> modifySerie(int serieId, int reps, double weight) throws Exception{
        SerieDto serieDto = new SerieDto(serieId, reps, weight);
        List<SerieModel> serieModelListResponse;
        try{
            Response<AddSerieResponseDto> response = RetrofitUtils.getRetrofitUtils().modifySerie(serieDto, getApiKey()).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                serieModelListResponse = SerieModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return serieModelListResponse;
    }

    public List<SerieModel> getSerieListOfExerciseAdded(int exerciseId) throws Exception{
        try{
            List<CategoryModel> categoryList = trainingResponseCacheByDate.get(currentDate);
            for(int i = 0; i < categoryList.size(); i++){
                CategoryModel category = categoryList.get(i);
                List<ExerciseModel> exerciseList = category.getExerciseList();
                for(int j = 0; j < exerciseList.size(); j++){
                    ExerciseModel exercise = exerciseList.get(j);
                    if(exercise.getId() == exerciseId){
                        return exercise.getSerieList();
                    }
                }
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    public List<SerieModel> deleteSerie(int serieId) throws Exception{
        List<SerieModel> serieModelListResponse;
        try{
           Response<AddSerieResponseDto> response = RetrofitUtils.getRetrofitUtils().deleteSerie(serieId, getApiKey()).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                serieModelListResponse = SerieModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return serieModelListResponse;
        }
    }

