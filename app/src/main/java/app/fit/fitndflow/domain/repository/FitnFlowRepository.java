package app.fit.fitndflow.domain.repository;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.model.UserModel;

public interface FitnFlowRepository extends CommonRepository {
    UserModel registerUser(String userName, String email, String premium) throws Exception;
    List<CategoryModel> getCategoryList() throws Exception;
    List<CategoryModel> deleteCategory(Integer integer) throws Exception;

    List<CategoryModel> getTrainingList(String date) throws Exception;
    List<CategoryModel> updateCurrentTrainingListCache() throws Exception;
    List<CategoryModel> addNewCategory(String categoryName, String language) throws Exception;
    List<CategoryModel> modifyCategory(String categoryName, String language, int categoryId, String imageUrl) throws Exception;
    List<ExerciseModel> addNewExercise(String exerciseName, String language, int categoryId) throws Exception;
    List<ExerciseModel> modifyExercise(int exerciseId, String exerciseName, String language, int categoryId) throws Exception;
    List<ExerciseModel> deleteExercise(Integer integer) throws Exception;
    List<SerieModel> addNewSerie(int reps, double weight, int exerciseId) throws Exception;
    List<SerieModel> modifySerie(int serieId, int reps, double weight) throws Exception;
    List<SerieModel> getSerieListOfExerciseAdded(int exerciseid) throws Exception;
    List<SerieModel> deleteSerie(int serieId) throws Exception;
}

