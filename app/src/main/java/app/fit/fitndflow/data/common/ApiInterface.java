package app.fit.fitndflow.data.common;


import java.util.List;

import app.fit.fitndflow.data.dto.RazaApi;
import app.fit.fitndflow.data.dto.UserDto;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    String URL_BASE = "https://localhost:5000/";

    @GET("breeds")
    Observable<List<RazaApi>> getRazas();

    @POST("register/")
    Observable<UserDto> register(@Body UserDto user);

}
