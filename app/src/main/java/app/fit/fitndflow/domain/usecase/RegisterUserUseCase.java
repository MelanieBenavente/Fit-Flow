package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.model.mapper.UserModelMapper;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class RegisterUserUseCase extends FitUseCase<UserModel, UserModel> {

    private FitnFlowRepository fitnFlowRepository = new FitnFlowRepositoryImpl();
    private Context context;

    public RegisterUserUseCase(UserModel inputParams, Context context) {
        super(inputParams);
        this.context = context;
    }

    @Override
    public Single<UserModel> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try {
                UserDto userDto = new UserDto();
                //converting the usermodel to userdto
                if (inputParams != null) {
                    userDto.apiKey = inputParams.getApiKey();
                    userDto.email = inputParams.getEmail();
                    userDto.userNAme = inputParams.getName();
                }
                UserDto response = fitnFlowRepository.registerUser(userDto);
                //When server responses, save apikey in sessionPRefs
                SharedPrefs.saveApikeyToSharedPRefs(context, response.apiKey);
                //map from dto to model before sending
                UserModel responseMapped = UserModelMapper.toModel(response);
                //emit response OnSucess
                emitter.onSuccess(responseMapped);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}

