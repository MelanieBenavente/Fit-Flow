package app.fit.fitndflow.data.repository;

import java.util.List;

import app.fit.fitndflow.data.common.RetrofitUtils;
import app.fit.fitndflow.data.common.model.ExcepcionApi;
import app.fit.fitndflow.data.dto.RazaApi;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import retrofit2.Call;
import retrofit2.Response;

public class FitnFlowRepositoryImpl implements FitnFlowRepository {
    @Override
    public Call<List<RazaApi>> requestRazaList() {
        return RetrofitUtils.getRetrofitUtils().getRazas();
    }

    @Override
    public UserDto registerUser(UserDto userDto) throws Exception{
        Response<UserDto> response;
        try {
            response = RetrofitUtils.getRetrofitUtils().register(userDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code());
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return response.body();
    }
}
