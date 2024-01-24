package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.ExerciseModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class DeleteExerciseUseCase extends FitUseCase<Integer, List<ExerciseModel>> {

    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public DeleteExerciseUseCase(int inputParams, Context context, FitnFlowRepository fitnFlowRepository){
        super(inputParams);
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }

    @Override
    public Single<List<ExerciseModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try{
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<ExerciseModel> response = fitnFlowRepository.deleteExercise(inputParams, apiKey);
                emitter.onSuccess(response);
            } catch(Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
