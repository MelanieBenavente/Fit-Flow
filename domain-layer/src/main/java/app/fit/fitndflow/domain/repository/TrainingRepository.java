package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;

public interface TrainingRepository extends CommonRepository {
    ExerciseModel addNewSerie(int reps, double weight, int exerciseId) throws Exception;
    ExerciseModel modifySerie(int serieId, int reps, double weight) throws Exception;
    ExerciseModel deleteSerie(int serieId) throws Exception;
    List<CategoryModel> getTrainingListAndUpdateCache(String date) throws Exception;
    List<CategoryModel> updateCurrentTrainingListCache() throws Exception;
    List<SerieModel> getSerieListOfExerciseAdded(int exerciseid) throws Exception;
}
