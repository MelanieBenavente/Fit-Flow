package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.data.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.model.UserModel;

public interface FitnFlowRepository extends CommonRepository {
    UserModel registerUser(UserDto userDto) throws Exception;
    List<CategoryModel> getCategoryList(String apiKey) throws Exception;
    List<CategoryModel> deleteCategory(Integer integer, String apikey) throws Exception;

    List<CategoryModel> getTrainingList(String date, String apiKey) throws Exception;
    List<CategoryModel> updateCurrentTrainingListCache(String apiKey) throws Exception;
    List<CategoryModel> addNewCategory(String categoryName, String language, String apiKey) throws Exception;
    List<CategoryModel> modifyCategory(String categoryName, String language, int categoryId, String imageUrl, String apiKey) throws Exception;
    List<ExerciseModel> addNewExercise(String exerciseName, String language, int categoryId, String apiKey) throws Exception;
    List<ExerciseModel> modifyExercise(int exerciseId, String exerciseName, String language, int categoryId, String apiKey) throws Exception;
    List<ExerciseModel> deleteExercise(Integer integer, String apiKey) throws Exception;
    List<SerieModel> addNewSerie(int reps, double weight, int exerciseId, String apiKey) throws Exception;
    List<SerieModel> modifySerie(int serieId, int reps, double weight, String apiKey) throws Exception;
    List<SerieModel> getSerieListOfExerciseAdded(int exerciseid) throws Exception;
    List<SerieModel> deleteSerie(int serieId, String apiKey) throws Exception;
}

