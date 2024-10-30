package app.fit.fitndflow.data.exercises.repositoryImpl;

import android.content.Context;

import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource;
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource;
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.common.dto.ExerciseDto;
import java.util.List;
import app.fit.fitndflow.data.common.mapper.ExerciseModelMapperKt;
import app.fit.fitndflow.data.common.mapper.StringInLanguagesMapperKt;
import app.fit.fitndflow.data.common.model.ExcepcionApi;
import app.fit.fitndflow.data.exercises.dto.AddExerciseDto;
import app.fit.fitndflow.data.exercises.dto.ModifyExerciseDto;
import app.fit.fitndflow.data.exercises.model.ExercisesApiInterface;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.repository.ExercisesRepository;
import retrofit2.Response;

public class ExercisesRepositoryImpl implements ExercisesRepository {
    private TrainingLocalDataSource trainingLocalDataSource;
    private CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource;
    private Context mContext;
    private ExercisesApiInterface apiInterface;

    public ExercisesRepositoryImpl(Context context, ExercisesApiInterface apiInterface, CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource, TrainingLocalDataSource trainingLocalDataSource) {
        this.mContext = context;
        this.apiInterface = apiInterface;
        this.categoriesAndExercisesLocalDataSource = categoriesAndExercisesLocalDataSource;
        this.trainingLocalDataSource = trainingLocalDataSource;
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
            Response<List<ExerciseDto>> response = apiInterface.modifyExercise(modifyExerciseDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                availableExerciseListResponse = ExerciseModelMapperKt.toModel(response.body());
                trainingLocalDataSource.cleanCache();
                categoriesAndExercisesLocalDataSource.cleanCache();
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
            Response<List<ExerciseDto>> response = apiInterface.deleteExercise(exerciseId).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                availableExerciseListResponse = ExerciseModelMapperKt.toModel(response.body());
                trainingLocalDataSource.cleanCache();
                categoriesAndExercisesLocalDataSource.cleanCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableExerciseListResponse;
    }

}
