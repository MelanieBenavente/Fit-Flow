package app.fit.fitndflow.data.repository;

import java.util.List;

import app.fit.fitndflow.data.common.RetrofitUtils;
import app.fit.fitndflow.data.dto.RazaApi;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Observable;

public class FitnFlowRepositoryImpl implements FitnFlowRepository {
    @Override
    public Observable<List<RazaApi>> requestRazaList() {
        return RetrofitUtils.getRetrofitUtils().getRazas();
    }

    @Override
    public Observable<UserDto> registerUser(UserDto userDto) {
        return RetrofitUtils.getRetrofitUtils().register(userDto);
    }
}
