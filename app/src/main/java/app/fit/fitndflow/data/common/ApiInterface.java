package app.fit.fitndflow.data.common;


import java.util.List;

import app.fit.fitndflow.data.dto.RazaApi;
import app.fit.fitndflow.data.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    String URL_BASE = "http://192.168.1.16:5000/";

    @GET("breeds")
    Call<List<RazaApi>> getRazas();

    @POST("register/")
    Call<UserDto> register(@Body UserDto user);

}
