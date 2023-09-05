package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.dto.StringInLanguages;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ExcerciseDto;
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExcerciseModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class AddCategoryUseCase extends FitUseCase<CategoryModel, Boolean> {

    private FitnFlowRepository fitnFlowRepository = new FitnFlowRepositoryImpl();
    private Context context;

    public AddCategoryUseCase(Context context, CategoryModel inputParams) {
        super(inputParams);
        this.context = context;
    }

    @Override
    public Single<Boolean> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try {
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<ExcerciseDto> excerciseDtoList = new ArrayList<>();
                for (int i = 0; i < inputParams.getExcerciseList().size(); i++) {
                    ExcerciseModel excerciseModel = inputParams.getExcerciseList().get(i);
                    ExcerciseDto excerciseDto = new ExcerciseDto();
                    excerciseDto.id = excerciseModel.getId();
                    excerciseDto.exerciseName = new StringInLanguages();
                    excerciseDto.exerciseName.spanish = excerciseModel.getName();
                    excerciseDtoList.add(excerciseDto);
                }
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.id = inputParams.getId();
                categoryDto.name = new StringInLanguages();
                categoryDto.name.spanish = inputParams.getName();
                categoryDto.excerciseDtoList = excerciseDtoList;

                Boolean response = fitnFlowRepository.saveCategoryAndExcercises(categoryDto, apiKey);
                emitter.onSuccess(response);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
