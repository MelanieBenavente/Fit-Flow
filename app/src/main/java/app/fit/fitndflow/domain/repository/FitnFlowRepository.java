package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.data.dto.RazaApi;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.common.repository.CommonRepository;
import retrofit2.Call;

public interface FitnFlowRepository extends CommonRepository {
    Call<List<RazaApi>> requestRazaList();

    UserDto registerUser(UserDto userDto) throws Exception;
}
