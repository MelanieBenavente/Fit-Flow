package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.dto.UserDto;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class RegisterUserUseCase extends FitUseCase<UserModel, UserModel> {

    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public RegisterUserUseCase(UserModel inputParams, Context context, FitnFlowRepository fitnFlowRepository) {
        super(inputParams);
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }

    @Override
    public Single<UserModel> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try {
                UserDto userDto = new UserDto(null, null, null, null);
                //converting the usermodel to userdto
                if (inputParams != null) {
                    userDto.setApiKey(inputParams.getApiKey());
                    userDto.setEmail(inputParams.getEmail());
                    userDto.setUserName(inputParams.getName());
                }
                UserModel response = fitnFlowRepository.registerUser(userDto);
                //When server responses, save apikey in sessionPRefs
                SharedPrefs.saveApikeyToSharedPRefs(context, response.getApiKey());
                //map from dto to model before sending
                //emit response OnSucess
                emitter.onSuccess(response);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}

