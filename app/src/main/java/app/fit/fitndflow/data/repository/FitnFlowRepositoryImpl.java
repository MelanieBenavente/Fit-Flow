package app.fit.fitndflow.data.repository;

import java.util.List;

import app.fit.fitndflow.data.common.RetrofitUtils;
import app.fit.fitndflow.data.common.model.ExcepcionApi;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.model.mapper.CategoryModelKTMapper;
import app.fit.fitndflow.domain.model.mapper.UserModelMapper;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import retrofit2.Response;

public class FitnFlowRepositoryImpl implements FitnFlowRepository {

    private List<CategoryModel> categoryListCachedResponse;


    @Override
    public UserModel registerUser(UserDto userDto) throws Exception {
        Response<UserDto> response;
        try {
            response = RetrofitUtils.getRetrofitUtils().register(userDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
            if(response != null && response.body() != null) {
                return UserModelMapper.toModel(response.body());
            } else {
                throw new Exception("Error register");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public List<CategoryModel> getCategoryList(String apiKey) throws Exception {
        if (categoryListCachedResponse == null) {
            Response<List<CategoryDto>> response;
            try {
                response = RetrofitUtils.getRetrofitUtils().getCategoryDtoList(apiKey).execute();
                if (response != null && !response.isSuccessful()) {
                    throw new ExcepcionApi(response.code());
                }
                if(response != null && response.body() != null) {
                    categoryListCachedResponse = CategoryModelKTMapper.toModel(response.body());
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        return categoryListCachedResponse;
    }

    @Override
    public Boolean saveCategoryAndExercises(CategoryDto categoryDto, String apikey) throws Exception {
        try {
            Response<CategoryDto> response = RetrofitUtils.getRetrofitUtils().saveCategory(categoryDto, apikey).execute();
            if (response.isSuccessful()) {
                deleteCache();
                return true;
            } else {
                throw new Exception(new Exception("Error from Server"));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public Boolean deleteCategoryAndExercises(Integer categoryId, String apikey) throws Exception {
        try {
            Response<CategoryDto> response = RetrofitUtils.getRetrofitUtils().deleteCategory(categoryId, apikey).execute();
            if (response.isSuccessful()) {
                deleteCache();
                return true;
            } else {
                throw new Exception(new Exception("Error from Server"));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void deleteCache(){
        categoryListCachedResponse = null;
    }
}
