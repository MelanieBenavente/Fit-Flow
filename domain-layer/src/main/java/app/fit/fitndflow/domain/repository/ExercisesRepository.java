package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.ExerciseModel;

public interface ExercisesRepository extends CommonRepository {
    List<ExerciseModel> addNewExercise(String exerciseName, String language, int categoryId) throws Exception;
    List<ExerciseModel> modifyExercise(int exerciseId, String exerciseName, String language, int categoryId) throws Exception;
    List<ExerciseModel> deleteExercise(Integer integer) throws Exception;
}
