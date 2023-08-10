package app.fit.fitndflow.domain.usecase;

import java.util.List;

import app.fit.fitndflow.data.dto.RazaApi;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.common.model.None;
import app.fit.fitndflow.domain.common.usecase.CommonUseCase;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Observable;

public class RegisterUserUseCase extends CommonUseCase<UserModel, Observable<UserDto>, FitnFlowRepository> {

    public RegisterUserUseCase() {
        super(new FitnFlowRepositoryImpl());
    }

    @Override
    public Observable<UserDto> executeUseCase(UserModel input) {
        UserDto userDto = new UserDto();
        if (input != null){
            userDto.apiKey = input.getApiKey();
            userDto.email = input.getEmail();
            userDto.userNAme = input.getName();
        }
        return repository.registerUser(userDto);
    }
}
