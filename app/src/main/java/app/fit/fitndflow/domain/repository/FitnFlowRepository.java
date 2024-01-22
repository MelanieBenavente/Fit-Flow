package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.data.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.CategoryModelInLanguages;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.UserModel;

public interface FitnFlowRepository extends CommonRepository {
    UserModel registerUser(UserDto userDto) throws Exception;
    List<CategoryModel> getCategoryList(String apiKey) throws Exception;
    Boolean saveCategoryAndExercises(CategoryDto categoryDto, String apikey) throws Exception;
    List<CategoryModel> deleteCategory(Integer integer, String apikey) throws Exception;

    List<CategoryModel> getTrainingList(String apiKey) throws Exception;

    List<CategoryModel> addNewCategory(StringInLanguagesDto categoryName, String apiKey) throws Exception;
    List<CategoryModel> modifyCategory(CategoryModelInLanguages category, String apiKey) throws Exception;
    List<ExerciseModel> addNewExercise(StringInLanguagesDto exerciseName, int categoryId, String apiKey) throws Exception;
}

