package app.fit.fitndflow.data.trainings.model;

import java.util.List;

import app.fit.fitndflow.data.common.dto.CategoryDto;
import app.fit.fitndflow.data.common.dto.ExerciseDto;
import app.fit.fitndflow.data.common.dto.SerieDto;
import app.fit.fitndflow.data.trainings.dto.AddSerieRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TrainingsApiInterface {
    String KEY_PARAM_GET_DATE_TRAINING = "date";
    String KEY_PARAM_GET_ID = "id";

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
