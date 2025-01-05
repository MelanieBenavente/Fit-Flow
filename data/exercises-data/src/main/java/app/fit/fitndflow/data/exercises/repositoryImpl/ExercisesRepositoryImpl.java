package app.fit.fitndflow.data.exercises.repositoryImpl;

import android.content.Context;

import com.fit.fitndflow.app.domain.common.models.ExerciseModel;
import com.fit.fitndflow.app.domain.exercises.repository.ExercisesRepository;

import java.util.List;

import app.fit.fitndflow.data.common.datasource.CategoriesAndExercisesLocalDataSource;
import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource;
import app.fit.fitndflow.data.common.dto.ExerciseDto;
import app.fit.fitndflow.data.common.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.common.mapper.ExerciseModelMapperKt;
import app.fit.fitndflow.data.common.mapper.StringInLanguagesMapperKt;
import app.fit.fitndflow.data.exercises.datasource.remote.ExerciseRemoteDataSource;

public class ExercisesRepositoryImpl implements ExercisesRepository {
    private TrainingLocalDataSource trainingLocalDataSource;
    private CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource;
    private Context mContext;
    private ExerciseRemoteDataSource exerciseRemoteDataSource;

    public ExercisesRepositoryImpl(Context context, ExerciseRemoteDataSource exerciseRemoteDataSource, CategoriesAndExercisesLocalDataSource categoriesAndExercisesLocalDataSource, TrainingLocalDataSource trainingLocalDataSource) {
        this.mContext = context;
        this.exerciseRemoteDataSource = exerciseRemoteDataSource;
        this.categoriesAndExercisesLocalDataSource = categoriesAndExercisesLocalDataSource;
        this.trainingLocalDataSource = trainingLocalDataSource;
    }

    @Override
    public List<ExerciseModel> addNewExercise(String exerciseName, String language, int categoryId) throws Exception {
        StringInLanguagesDto stringInLanguages = StringInLanguagesMapperKt.convertToStringInLanguages(language, exerciseName);
        List<ExerciseModel> availableExerciseListResponse;
        List<ExerciseDto> response;
        try {
            response = exerciseRemoteDataSource.addNewExercise(stringInLanguages, categoryId);
            availableExerciseListResponse = ExerciseModelMapperKt.toModel(response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableExerciseListResponse;
    }

    @Override
    public List<ExerciseModel> modifyExercise(int exerciseId, String exerciseName, String language, int categoryId) throws Exception {
        StringInLanguagesDto stringInLanguages = StringInLanguagesMapperKt.convertToStringInLanguages(language, exerciseName);
        List<ExerciseModel> availableExerciseListResponse;
        List<ExerciseDto> response;
        try {
            response = exerciseRemoteDataSource.modifyExercise(exerciseId, stringInLanguages, categoryId);
            availableExerciseListResponse = ExerciseModelMapperKt.toModel(response);
            trainingLocalDataSource.cleanCache();
            categoriesAndExercisesLocalDataSource.cleanCache();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableExerciseListResponse;
    }

    @Override
    public List<ExerciseModel> deleteExercise(Integer exerciseId) throws Exception {
        List<ExerciseModel> availableExerciseListResponse;
        List<ExerciseDto> response;
        try {
            response = exerciseRemoteDataSource.deleteExercise(exerciseId);
            availableExerciseListResponse = ExerciseModelMapperKt.toModel(response);
            trainingLocalDataSource.cleanCache();
            categoriesAndExercisesLocalDataSource.cleanCache();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return availableExerciseListResponse;
    }

}
