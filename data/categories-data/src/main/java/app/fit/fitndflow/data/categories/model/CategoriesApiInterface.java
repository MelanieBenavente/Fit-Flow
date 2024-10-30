package app.fit.fitndflow.data.categories.model;

import java.util.List;

import app.fit.fitndflow.data.categories.dto.AddCategoryDto;
import app.fit.fitndflow.data.common.dto.CategoryDto;
import app.fit.fitndflow.data.categories.dto.ModifyCategoryDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoriesApiInterface {
    String URL_BASE = "http://fitnflowapi-env.eba-8aaimaij.eu-west-3.elasticbeanstalk.com/";

    //LLAMADAS CATEGORIA
    @GET("summary/categories/")
    Call<List<CategoryDto>> getCategoryDtoList();
    @DELETE("category/{id}")
    Call <List<CategoryDto>> deleteCategory(@Path("id") int id);
    @POST("category/add")
    Call<List<CategoryDto>> addNewCategory(@Body AddCategoryDto addCategoryDto);
    @POST("category/update")
    Call<List<CategoryDto>> modifyCategory(@Body ModifyCategoryDto modifyCategoryDto);
}
