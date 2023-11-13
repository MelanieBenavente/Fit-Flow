package app.fit.fitndflow.domain.model.mapper

import app.fit.fitndflow.data.dto.UserDto
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