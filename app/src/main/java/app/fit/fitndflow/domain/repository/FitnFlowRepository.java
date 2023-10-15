package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.CategoryModelKT;
import app.fit.fitndflow.domain.model.UserModel;

public interface FitnFlowRepository extends CommonRepository {
    UserModel registerUser(UserDto userDto) throws Exception;
    List<CategoryModelKT> getCategoryList(String apiKey) throws Exception;
    Boolean saveCategoryAndExcercises(CategoryDto categoryDto, String apikey) throws Exception;
    Boolean deleteCategoryAndExcercises(Integer integer, String apikey) throws Exception;
}
