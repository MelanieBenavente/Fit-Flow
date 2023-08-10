package app.fit.fitndflow.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDto implements Serializable {
    @SerializedName("userName")
    public String userNAme;

    @SerializedName("apiKey")
    public
    String apiKey;

    @SerializedName("email")
    public String email;

    @SerializedName("premium")
    public String premium;


}
