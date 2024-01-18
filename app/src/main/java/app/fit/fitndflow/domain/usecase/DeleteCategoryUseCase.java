package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class DeleteCategoryUseCase extends FitUseCase<Integer, List<CategoryModel>> {

    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public DeleteCategoryUseCase(int inputParams, Context context, FitnFlowRepository fitnFlowRepository) {
        super(inputParams);
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }

    @Override
    public Single<List<CategoryModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try{
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<CategoryModel> response = fitnFlowRepository.deleteCategoryAndExercises(inputParams, apiKey);
                emitter.onSuccess(response);
            } catch(Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
