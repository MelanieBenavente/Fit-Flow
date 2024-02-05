package app.fit.fitndflow.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
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
import app.fit.fitndflow.data.dto.trainings.AddSerieRequestDto;
import app.fit.fitndflow.data.dto.trainings.AddSerieResponseDto;
import app.fit.fitndflow.data.dto.trainings.SerieDto;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.CategoryModelInLanguages;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.ExerciseModelInLanguages;
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
    private HashMap<String, List<CategoryModel>> trainingResponseByCategories = new HashMap<>();

    //Creo una función estática mediante el patrón Singleton para que solamente se pueda instanciar UN repositorio.
    // Desde los viewModels instanciamos el repositorio llamando a esta función y así nos aseguramos que se refieran mismo repo en ambos casos:
    public static FitnFlowRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new FitnFlowRepositoryImpl();
        }
        return instance;
    }

    private  void removeAllDataFromHashMapCache(){
        trainingResponseByCategories.clear();
    }

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
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public List<CategoryModel> getCategoryList(String apiKey) throws Exception {
        if (availableCategoryListCachedResponse == null) {
            Response<List<CategoryDto>> response;
            try {
                response = RetrofitUtils.getRetrofitUtils().getCategoryDtoList(apiKey).execute();
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
    public List<CategoryModel> addNewCategory(StringInLanguagesDto categoryName, String apiKey) throws Exception {
        try {

            AddCategoryDto addCategoryDto = new AddCategoryDto(categoryName);
            Response<List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().addNewCategory(addCategoryDto, apiKey).execute();

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
    public List<CategoryModel> modifyCategory(CategoryModelInLanguages category, String apiKey) throws Exception {
        try {
            ModifyCategoryDto modifyCategoryDto = new ModifyCategoryDto(category.getId(), category.getName(), category.getImageUrl());
            Response<List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().modifyCategory(modifyCategoryDto, apiKey).execute();

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
    public List<CategoryModel> deleteCategory(Integer categoryId, String apikey) throws Exception {
        try {
            Response <List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().deleteCategory(categoryId, apikey).execute();
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
    public List<ExerciseModel> addNewExercise(StringInLanguagesDto exerciseName, int categoryId, String apiKey) throws Exception {
       List<ExerciseModel> availableExerciseListResponse;
        try {
            AddExerciseDto addExerciseDto = new AddExerciseDto(exerciseName, categoryId);
            Response<List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().addNewExercise(addExerciseDto, apiKey).execute();

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
    public List<ExerciseModel> modifyExercise(ExerciseModelInLanguages exercise, String apiKey) throws Exception {
        List<ExerciseModel> availableExerciseListResponse;
        try {
            ModifyExerciseDto modifyExerciseDto = new ModifyExerciseDto(exercise.getId(), exercise.getName(), exercise.getCategoryId());
            Response <List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().modifyExercise(modifyExerciseDto, apiKey).execute();
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
    public List<ExerciseModel> deleteExercise(Integer exerciseId, String apikey) throws Exception {
        List<ExerciseModel> availableExerciseListResponse;
        try {
            Response <List<ExerciseDto>> response = RetrofitUtils.getRetrofitUtils().deleteExercise(exerciseId, apikey).execute();
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

    public List<SerieModel> addNewSerie(AddSerieRequestDto addSerieRequestDto, String apiKey) throws Exception {
        List<SerieModel> serieListResponse;
        try{
            Response <AddSerieResponseDto> response = RetrofitUtils.getRetrofitUtils().addNewSerie(addSerieRequestDto, apiKey).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                serieListResponse = SerieModelMapperKt.toModel(response.body());
                trainingResponseByCategories.remove(addSerieRequestDto.getDate());
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
    public List<CategoryModel> getTrainingList(String date, String apiKey) throws Exception {
        if(trainingResponseByCategories.get(date) == null){
            try {
                Response<List<CategoryDto>> response = RetrofitUtils.getRetrofitUtils().getCategoriesAndTrainings(date, apiKey).execute();
                if(response != null){
                    trainingResponseByCategories.put(date, CategoryModelMapperKt.toModel(response.body()));}

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
        return trainingResponseByCategories.get(date);
    }
    public List<SerieModel> modifySerie(SerieDto serieDto, String apiKey) throws Exception{
        List<SerieModel> serieListResponse;
        try{
            Response<AddSerieResponseDto> response = RetrofitUtils.getRetrofitUtils().modifySerie(serieDto, apiKey).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                serieListResponse = SerieModelMapperKt.toModel(response.body());
                removeAllDataFromHashMapCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return serieListResponse;
    }
    public List<SerieModel> getSerieListOfExerciseAdded(String date, int exerciseId) throws Exception{
        try{
            List<CategoryModel> categoryList = trainingResponseByCategories.get(date);
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
}
