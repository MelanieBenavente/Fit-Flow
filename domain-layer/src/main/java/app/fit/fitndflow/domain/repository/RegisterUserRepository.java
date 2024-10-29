package app.fit.fitndflow.domain.repository;

import app.fit.fitndflow.domain.common.repository.CommonRepository;
import app.fit.fitndflow.domain.model.UserModel;

public interface RegisterUserRepository extends CommonRepository {
    UserModel registerUser(String userName, String email, String premium) throws Exception;
    String getApikey() throws Exception;
}

