package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.dto.StringInLanguagesDto;
import app.fit.fitndflow.data.dto.categories.CategoryDto;
import app.fit.fitndflow.data.dto.categories.ExerciseDto;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class AddCategoryUseCase extends FitUseCase<CategoryModel, Boolean> {

    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public AddCategoryUseCase(Context context, CategoryModel inputParams, FitnFlowRepository fitnFlowRepository) {
        super(inputParams);
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }

    @Override
    public Single<Boolean> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try {
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<ExerciseDto> exerciseDtoList = new ArrayList<>();
                if(inputParams.getExerciseList() != null) {
                    for (int i = 0; i < inputParams.getExerciseList().size(); i++) {
                        ExerciseModel exerciseModel = inputParams.getExerciseList().get(i);
                        ExerciseDto exerciseDto = new ExerciseDto(null, null);
                        exerciseDto.setId(exerciseModel.getId());
                        exerciseDto.setExerciseName(new StringInLanguagesDto(null, null));
                        exerciseDto.getExerciseName().setSpanish(exerciseModel.getName());
                        exerciseDtoList.add(exerciseDto);
                    }
                }
                CategoryDto categoryDto = new CategoryDto(null, new StringInLanguagesDto(inputParams.getName(), null), exerciseDtoList);
                categoryDto.setId(inputParams.getId());


                Boolean response = fitnFlowRepository.saveCategoryAndExercises(categoryDto, apiKey);
                emitter.onSuccess(response);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
