package app.fit.fitndflow.data.user.model;

import app.fit.fitndflow.data.user.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiInterface {

    //LLAMADA USUARIO
    @POST("register/")
    Call<UserDto> register(@Body UserDto user);
}
