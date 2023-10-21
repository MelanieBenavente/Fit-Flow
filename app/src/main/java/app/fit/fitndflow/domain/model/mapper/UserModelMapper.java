package app.fit.fitndflow.domain.model.mapper;

import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.model.UserModel;

public class UserModelMapper {
    public static UserModel toModel(UserDto userDTO) {
        UserModel userModel = new UserModel(null, null, null, null);
        if (userDTO != null) {
            userModel.setApiKey(userDTO.getApiKey());
            userModel.setName(userDTO.getUserName());
            userModel.setEmail(userDTO.getEmail());
        }
        return userModel;
    }
}
