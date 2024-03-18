package app.fit.fitndflow.domain.usecase;

import static app.fit.fitndflow.domain.Utils.convertToStringInLanguages;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.model.ExerciseModelInLanguages;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class ModifyExerciseUseCase extends FitUseCase<ExerciseModelInLanguages, List<ExerciseModel>> {
    public static final String SPANISH = "es";
    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public ModifyExerciseUseCase(Context context, int exerciseId, String language, String nameExercise, int categoryId, FitnFlowRepository fitnFlowRepository){
        super(new ExerciseModelInLanguages(exerciseId, convertToStringInLanguages(language, nameExercise), categoryId));
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }
    @Override
    public Single<List<ExerciseModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try{
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<ExerciseModel> response = fitnFlowRepository.modifyExercise(inputParams, apiKey);
                fitnFlowRepository.updateCurrentTrainingListCache(apiKey);
                emitter.onSuccess(response);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
