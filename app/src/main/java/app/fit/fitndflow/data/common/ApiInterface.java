package app.fit.fitndflow.data.common;


import java.util.List;

import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.AddCategoryDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.exercises.AddExerciseDto;
import app.fit.fitndflow.data.dto.exercises.ExerciseDto;
import app.fit.fitndflow.data.dto.categories.ModifyCategoryDto;
import app.fit.fitndflow.data.dto.exercises.ModifyExerciseDto;
import app.fit.fitndflow.data.dto.trainings.AddSerieRequestDto;
import app.fit.fitndflow.data.dto.trainings.AddSerieResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    String URL_BASE = "http://192.168.1.16:5000/";
    String KEY = "auth-token";

    //LLAMADA USUARIO
    @POST("register/")
    Call<UserDto> register(@Body UserDto user);

    //LLAMADAS CATEGORIA
    @GET("summary/categories/")
    Call<List<CategoryDto>> getCategoryDtoList(@Header(KEY) String apiKey);

    @POST("category/")
    Call <CategoryDto> saveCategory(@Body CategoryDto categoryDto, @Header(KEY) String apiKey);

    @DELETE("category/{id}")
    Call <List<CategoryDto>> deleteCategory(@Path("id") int id, @Header(KEY) String apiKey);

    @POST("category/add")
    Call<List<CategoryDto>> addNewCategory(@Body AddCategoryDto addCategoryDto, @Header(KEY) String apiKey);

    @POST("category/update")
    Call<List<CategoryDto>> modifyCategory(@Body ModifyCategoryDto modifyCategoryDto, @Header(KEY) String apiKey);

    //LLAMADAS EJERCICIOS
    @POST("exercise/add")
    Call<List<ExerciseDto>> addNewExercise(@Body AddExerciseDto addExerciseDto, @Header(KEY) String apiKey);

    @POST("exercise/update")
    Call<List<ExerciseDto>> modifyExercise(@Body ModifyExerciseDto modifyExerciseDto, @Header(KEY) String apiKey);

    @DELETE("exercise/{id}")
    Call<List<ExerciseDto>> deleteExercise(@Path("id") int exerciseId, @Header(KEY) String apikey);

    //LLAMADAS TRAINING SERIES
    @POST("training/addSerie")
    Call<AddSerieResponseDto> addNewSerie(@Body AddSerieRequestDto addSerieDto, @Header(KEY) String apiKey);
}
