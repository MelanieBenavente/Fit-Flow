package app.fit.fitndflow.data.trainings.repositoryImpl;

import android.content.Context;

import app.fit.fitndflow.data.common.datasource.TrainingLocalDataSource;
import app.fit.fitndflow.data.common.dto.CategoryDto;
import app.fit.fitndflow.data.common.dto.ExerciseDto;
import app.fit.fitndflow.data.common.dto.SerieDto;
import app.fit.fitndflow.data.common.mapper.CategoryModelMapperKt;
import app.fit.fitndflow.data.common.mapper.ExerciseModelMapperKt;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.common.model.ExcepcionApi;
import app.fit.fitndflow.data.trainings.datasource.remote.TrainingRemoteDataSource;
import app.fit.fitndflow.data.trainings.dto.AddSerieRequestDto;
import app.fit.fitndflow.data.trainings.dto.SerieForAddSerieRequestDto;
import app.fit.fitndflow.data.trainings.model.TrainingsApiInterface;

import com.fit.fitndflow.app.domain.common.models.CategoryModel;
import com.fit.fitndflow.app.domain.common.models.ExerciseModel;
import com.fit.fitndflow.app.domain.common.models.SerieModel;
import com.fit.fitndflow.app.domain.trainings.repository.TrainingRepository;

import retrofit2.Response;

public class TrainingRepositoryImpl implements TrainingRepository {
    private TrainingLocalDataSource trainingLocalDataSource;
    private Context mContext;
    private TrainingRemoteDataSource trainingRemoteDataSource;

    public TrainingRepositoryImpl(Context context, TrainingRemoteDataSource trainingRemoteDataSource, TrainingLocalDataSource trainingLocalDataSource) {
        this.mContext = context;
        this.trainingRemoteDataSource = trainingRemoteDataSource;
        this.trainingLocalDataSource = trainingLocalDataSource;
    }

    @Override
    public List<SerieModel> getSerieListOfExerciseAdded(int exerciseId) throws Exception {
        try {
            List<CategoryModel> categoryList = trainingLocalDataSource.getTrainingsByDate(trainingLocalDataSource.getCurrentDate());
            if (categoryList != null) {
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

    @Override
    public ExerciseModel addNewSerie(int reps, double weight, int exerciseId) throws Exception {
        ExerciseModel exerciseResponse;
        ExerciseDto response;
        try {
            response = trainingRemoteDataSource.addNewSerie(reps, weight, exerciseId, trainingLocalDataSource.getCurrentDate());
            exerciseResponse = ExerciseModelMapperKt.toModel(response);
            trainingLocalDataSource.cleanCache();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
    }

    @Override
    public ExerciseModel modifySerie(int serieId, int reps, double weight) throws Exception {
        ExerciseModel exerciseResponse;
        ExerciseDto response;
        try {
            response = trainingRemoteDataSource.modifySerie(serieId, reps, weight);
            exerciseResponse = ExerciseModelMapperKt.toModel(response);
            trainingLocalDataSource.cleanCache();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
    }

    @Override
    public ExerciseModel deleteSerie(int serieId) throws Exception {
        ExerciseModel exerciseResponse;
        ExerciseDto response;
        try {
            response = trainingRemoteDataSource.deleteSerie(serieId);
            exerciseResponse = ExerciseModelMapperKt.toModel(response);
            trainingLocalDataSource.cleanCache();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
    }

    @Override
    public List<CategoryModel> getTrainingListAndUpdateCache(String date) throws Exception {
        trainingLocalDataSource.setCurrentDate(date);
        List<CategoryDto> response;
        if (trainingLocalDataSource.getTrainingsByDate(date) == null) {
            try {
                response = trainingRemoteDataSource.getTrainingListAndUpdateCache(date);
                trainingLocalDataSource.replaceAllDataFromTrainingCache(date, CategoryModelMapperKt.toModel(response));
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
        return trainingLocalDataSource.getTrainingsByDate(date);
    }

    @Override
    public List<CategoryModel> updateCurrentTrainingListCache() throws Exception {
        return getTrainingListAndUpdateCache(trainingLocalDataSource.getCurrentDate());
    }
}
