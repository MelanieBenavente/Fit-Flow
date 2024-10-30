package app.fit.fitndflow.data.user.mapper

import app.fit.fitndflow.data.user.dto.UserDto
import app.fit.fitndflow.domain.model.UserModel

class UserModelMapperKt {

    companion object {
        @JvmStatic
        fun toModel(userDto : UserDto? = null) : UserModel {
            val userModel = UserModel(userDto?.apiKey, userDto?.userName, userDto?.email)
            return userModel
        }
    }
}