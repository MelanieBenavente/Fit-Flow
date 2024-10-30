package com.fit.fitndflow.app.domain.trainings.repository;

import com.fit.fitndflow.app.domain.common.models.CategoryModel;
import com.fit.fitndflow.app.domain.common.models.ExerciseModel;
import com.fit.fitndflow.app.domain.common.models.SerieModel;
import com.fit.fitndflow.app.domain.common.repository.CommonRepository;
import java.util.List;

public interface TrainingRepository extends CommonRepository {
    ExerciseModel addNewSerie(int reps, double weight, int exerciseId) throws Exception;
    ExerciseModel modifySerie(int serieId, int reps, double weight) throws Exception;
    ExerciseModel deleteSerie(int serieId) throws Exception;
    List<CategoryModel> getTrainingListAndUpdateCache(String date) throws Exception;
    List<CategoryModel> updateCurrentTrainingListCache() throws Exception;
    List<SerieModel> getSerieListOfExerciseAdded(int exerciseid) throws Exception;
}
