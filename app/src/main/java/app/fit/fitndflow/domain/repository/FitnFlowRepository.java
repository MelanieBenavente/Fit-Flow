package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.UserModel;

public interface FitnFlowRepository extends CommonRepository {
    UserModel registerUser(UserDto userDto) throws Exception;
    List<CategoryModel> getCategoryList(String apiKey) throws Exception;
    Boolean saveCategoryAndExercises(CategoryDto categoryDto, String apikey) throws Exception;
    Boolean deleteCategoryAndExercises(Integer integer, String apikey) throws Exception;
}
