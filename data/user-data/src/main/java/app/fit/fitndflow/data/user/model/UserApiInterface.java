package app.fit.fitndflow.data.user.model;

import app.fit.fitndflow.data.user.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiInterface {
    String URL_BASE = "http://fitnflowapi-env.eba-8aaimaij.eu-west-3.elasticbeanstalk.com/";

    //LLAMADA USUARIO
    @POST("register/")
    Call<UserDto> register(@Body UserDto user);
}
