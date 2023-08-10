package app.fit.fitndflow.domain.model.mapper;

import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.model.UserModel;

public class UserModelMapper {
    public static UserModel toModel(UserDto userDTO) {
        UserModel userModel = new UserModel();
        if (userDTO != null) {
            userModel.setApiKey(userDTO.apiKey);
            userModel.setName(userDTO.userNAme);
            userModel.setEmail(userDTO.email);
        }
        return userModel;
    }
}
