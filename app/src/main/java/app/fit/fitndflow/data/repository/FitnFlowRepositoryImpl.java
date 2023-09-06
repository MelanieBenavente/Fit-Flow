package app.fit.fitndflow.data.repository;

import java.util.List;

import app.fit.fitndflow.data.common.RetrofitUtils;
import app.fit.fitndflow.data.common.model.ExcepcionApi;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.model.mapper.CategoryModelMapper;
import app.fit.fitndflow.domain.model.mapper.UserModelMapper;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import retrofit2.Response;

public class FitnFlowRepositoryImpl implements FitnFlowRepository {

    @Override
    public UserModel registerUser(UserDto userDto) throws Exception {
        Response<UserDto> response;
        try {
            response = RetrofitUtils.getRetrofitUtils().register(userDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        UserModel mappedResponse = UserModelMapper.toModel(response.body());
        return mappedResponse;
    }

    @Override
    public List<CategoryModel> getCategoryList(String apiKey) throws Exception {
        Response<List<CategoryDto>> response;
        try {
            response = RetrofitUtils.getRetrofitUtils().getCategoryDtoList(apiKey).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        List<CategoryModel> mappedResponse = CategoryModelMapper.toModel(response.body());
        return mappedResponse;
    }

    @Override
    public Boolean saveCategoryAndExcercises(CategoryDto categoryDto, String apikey) throws Exception {
        try {
            CategoryDto response = RetrofitUtils.getRetrofitUtils().saveCategory(categoryDto, apikey).execute().body();
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
