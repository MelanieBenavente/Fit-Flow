package com.fit.fitndflow.app.domain.exercises.repository;

import com.fit.fitndflow.app.domain.common.models.ExerciseModel;
import com.fit.fitndflow.app.domain.common.repository.CommonRepository;
import java.util.List;

public interface ExercisesRepository extends CommonRepository {
    List<ExerciseModel> addNewExercise(String exerciseName, String language, int categoryId) throws Exception;
    List<ExerciseModel> modifyExercise(int exerciseId, String exerciseName, String language, int categoryId) throws Exception;
    List<ExerciseModel> deleteExercise(Integer integer) throws Exception;
}
