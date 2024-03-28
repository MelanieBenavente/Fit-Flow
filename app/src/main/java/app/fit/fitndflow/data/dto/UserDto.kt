package app.fit.fitndflow.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDto(@SerializedName("userName") var userName: String?, @SerializedName("email") var email: String?, @SerializedName("premium") var premium: String?, @SerializedName("apiKey") var apiKey: String?): Serializable
