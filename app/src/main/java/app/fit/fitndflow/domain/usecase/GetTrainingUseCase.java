package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.common.model.None;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class GetTrainingUseCase extends FitUseCase<String, List<CategoryModel>> {

    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public GetTrainingUseCase( Context context, String date, FitnFlowRepository fitnFlowRepository) {
        super(date);
        this.fitnFlowRepository = fitnFlowRepository;
        this.context = context;
    }

    @Override
    public Single<List<CategoryModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try {
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                if(apiKey != null) {
                    List<CategoryModel> response = fitnFlowRepository.getTrainingList(inputParams, apiKey);
                    emitter.onSuccess(response);
                } else {
                    emitter.onSuccess(new ArrayList<>());
                }
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
