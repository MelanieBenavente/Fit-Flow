package com.fit.fitndflow.data.common;


import java.util.List;

import com.fit.fitndflow.data.dto.UserDto;
import com.fit.fitndflow.data.dto.categories.AddCategoryDto;
import com.fit.fitndflow.data.dto.categories.CategoryDto;
import com.fit.fitndflow.data.dto.categories.ModifyCategoryDto;
import com.fit.fitndflow.data.dto.exercises.AddExerciseDto;
import com.fit.fitndflow.data.dto.exercises.ExerciseDto;
import com.fit.fitndflow.data.dto.exercises.ModifyExerciseDto;
import com.fit.fitndflow.data.dto.trainings.AddSerieRequestDto;
import com.fit.fitndflow.data.dto.trainings.AddSerieResponseDto;
import com.fit.fitndflow.data.dto.trainings.SerieDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    String URL_BASE = "http://fitnflowapi-env.eba-8aaimaij.eu-west-3.elasticbeanstalk.com/";
    String KEY_PARAM_GET_DATE_TRAINING = "date";
    String KEY_PARAM_GET_ID = "id";

    //LLAMADA USUARIO
    @POST("register/")
    Call<UserDto> register(@Body UserDto user);

    //LLAMADAS CATEGORIA
    @GET("summary/categories/")
    Call<List<CategoryDto>> getCategoryDtoList();
    @DELETE("category/{id}")
    Call <List<CategoryDto>> deleteCategory(@Path("id") int id);
    @POST("category/add")
    Call<List<CategoryDto>> addNewCategory(@Body AddCategoryDto addCategoryDto);
    @POST("category/update")
    Call<List<CategoryDto>> modifyCategory(@Body ModifyCategoryDto modifyCategoryDto);

    //LLAMADAS EJERCICIOS
    @POST("exercise/add")
    Call<List<ExerciseDto>> addNewExercise(@Body AddExerciseDto addExerciseDto);
    @POST("exercise/update")
    Call<List<ExerciseDto>> modifyExercise(@Body ModifyExerciseDto modifyExerciseDto);
    @DELETE("exercise/{id}")
    Call<List<ExerciseDto>> deleteExercise(@Path("id") int exerciseId);

    //LLAMADAS TRAINING SERIES
    @POST("training/addSerie")
    Call<ExerciseDto> addNewSerie(@Body AddSerieRequestDto addSerieDto);
    @GET("summary/trainings")
    Call<List<CategoryDto>> getCategoriesAndTrainings(@Query(KEY_PARAM_GET_DATE_TRAINING) String date);
    @POST("training/updateSerie")
    Call<ExerciseDto> modifySerie(@Body SerieDto serieDto);
    @DELETE("training/deleteSerie")
    Call<ExerciseDto> deleteSerie(@Query(KEY_PARAM_GET_ID) int serieId);
}
