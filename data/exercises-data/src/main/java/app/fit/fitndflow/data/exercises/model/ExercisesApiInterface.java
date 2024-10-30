package app.fit.fitndflow.data.exercises.model;

import java.util.List;

import app.fit.fitndflow.data.common.dto.ExerciseDto;
import app.fit.fitndflow.data.exercises.dto.AddExerciseDto;
import app.fit.fitndflow.data.exercises.dto.ModifyExerciseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExercisesApiInterface {
    String URL_BASE = "http://fitnflowapi-env.eba-8aaimaij.eu-west-3.elasticbeanstalk.com/";

    //LLAMADAS EJERCICIOS
    @POST("exercise/add")
    Call<List<ExerciseDto>> addNewExercise(@Body AddExerciseDto addExerciseDto);
    @POST("exercise/update")
    Call<List<ExerciseDto>> modifyExercise(@Body ModifyExerciseDto modifyExerciseDto);
    @DELETE("exercise/{id}")
    Call<List<ExerciseDto>> deleteExercise(@Path("id") int exerciseId);
}
