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
import app.fit.fitndflow.data.trainings.dto.AddSerieRequestDto;
import app.fit.fitndflow.data.trainings.dto.SerieForAddSerieRequestDto;
import app.fit.fitndflow.data.trainings.model.TrainingsApiInterface;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.repository.TrainingRepository;
import retrofit2.Response;

public class TrainingRepositoryImpl implements TrainingRepository {
    private TrainingLocalDataSource trainingLocalDataSource;
    private Context mContext;
    private TrainingsApiInterface apiInterface;

    public TrainingRepositoryImpl(Context context, TrainingsApiInterface apiInterface, TrainingLocalDataSource trainingLocalDataSource) {
        this.mContext = context;
        this.apiInterface = apiInterface;
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
        SerieForAddSerieRequestDto serieForAddSerieRequestDto = new SerieForAddSerieRequestDto(reps, weight, new ExerciseDto(exerciseId, null, null, null, null));
        AddSerieRequestDto addSerieRequestDto = new AddSerieRequestDto(trainingLocalDataSource.getCurrentDate(), serieForAddSerieRequestDto);
        ExerciseModel exerciseResponse;
        try {
            Response<ExerciseDto> response = apiInterface.addNewSerie(addSerieRequestDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseResponse = ExerciseModelMapperKt.toModel(response.body());
                trainingLocalDataSource.cleanCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
    }

    @Override
    public ExerciseModel modifySerie(int serieId, int reps, double weight) throws Exception {
        SerieDto serieDto = new SerieDto(serieId, reps, weight);
        ExerciseModel exerciseResponse;
        try {
            Response<ExerciseDto> response = apiInterface.modifySerie(serieDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseResponse = ExerciseModelMapperKt.toModel(response.body());
                trainingLocalDataSource.cleanCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
    }

    @Override
    public ExerciseModel deleteSerie(int serieId) throws Exception {
        ExerciseModel exerciseResponse;
        try {
            Response<ExerciseDto> response = apiInterface.deleteSerie(serieId).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if (response != null && response.body() != null) {
                exerciseResponse = ExerciseModelMapperKt.toModel(response.body());
                trainingLocalDataSource.cleanCache();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return exerciseResponse;
    }

    @Override
    public List<CategoryModel> getTrainingListAndUpdateCache(String date) throws Exception {
        trainingLocalDataSource.setCurrentDate(date);
        if (trainingLocalDataSource.getTrainingsByDate(date) == null) {
            try {
                Response<List<CategoryDto>> response = apiInterface.getCategoriesAndTrainings(date).execute();
                if (response != null) {
                    trainingLocalDataSource.replaceAllDataFromTrainingCache(date, CategoryModelMapperKt.toModel(response.body()));
                }

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
