package app.fit.fitndflow.domain.repository;

import java.util.List;

import app.fit.fitndflow.data.dto.RazaApi;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.common.repository.CommonRepository;
import io.reactivex.Observable;

public interface FitnFlowRepository extends CommonRepository {
    Observable<List<RazaApi>> requestRazaList();

    Observable<UserDto> registerUser(UserDto userDto);
}
