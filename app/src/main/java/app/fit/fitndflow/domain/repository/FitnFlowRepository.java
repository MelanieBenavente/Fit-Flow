package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.ItemModel;
import app.fit.fitndflow.domain.model.UserModel;

public interface FitnFlowRepository extends CommonRepository {
    UserModel registerUser(UserDto userDto) throws Exception;
    List<ItemModel> getCategoryList(String apiKey) throws Exception;
}
