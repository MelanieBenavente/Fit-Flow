package com.fit.fitndflow.data.dto.mapper

import app.fit.fitndflow.domain.model.UserModel
import com.fit.fitndflow.data.dto.UserDto

class UserModelMapperKt {

    companion object {
        @JvmStatic
        fun toModel(userDto : UserDto? = null) : UserModel {
            val userModel = UserModel(userDto?.apiKey, userDto?.userName, userDto?.email)
            return userModel
        }
    }
}