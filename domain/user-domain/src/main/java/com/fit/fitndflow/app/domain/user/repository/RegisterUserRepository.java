package com.fit.fitndflow.app.domain.user.repository;

import com.fit.fitndflow.app.domain.common.repository.CommonRepository;
import com.fit.fitndflow.app.domain.user.model.UserModel;

public interface RegisterUserRepository extends CommonRepository {
    UserModel registerUser(String userName, String email, String premium) throws Exception;
    String getApikey() throws Exception;
}

