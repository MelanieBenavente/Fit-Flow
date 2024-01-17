package app.fit.fitndflow.data.common;


import java.util.List;

import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.AddCategoryDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ModifyCategoryDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    String URL_BASE = "http://192.168.1.16:5000/";
    String KEY = "auth-token";

    @POST("register/")
    Call<UserDto> register(@Body UserDto user);

    @GET("summary/categories/")
    Call<List<CategoryDto>> getCategoryDtoList(@Header(KEY) String apiKey);

    @POST("category/")
    Call <CategoryDto> saveCategory(@Body CategoryDto categoryDto, @Header(KEY) String apiKey);

    @HTTP(method = "DELETE", path = "category/", hasBody = true)
    Call<CategoryDto> deleteCategory(@Body Integer categoryId, @Header(KEY) String apiKey);

    @POST("category/add")
    Call<List<CategoryDto>> addNewCategory(@Body AddCategoryDto addCategoryDto, @Header(KEY) String apiKey);

    @POST("category/update")
    Call<List<CategoryDto>> modifyCategory(@Body ModifyCategoryDto modifyCategoryDto, @Header(KEY) String apiKey);

}
