package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.common.model.None;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.CategoryModelKT;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class GetCategoriesUseCase extends FitUseCase<None, List<CategoryModelKT>> {

    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public GetCategoriesUseCase(Context context, FitnFlowRepository fitnFlowRepository) {
        super();
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }

    @Override
    public Single<List<CategoryModelKT>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try {
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<CategoryModelKT> response = fitnFlowRepository.getCategoryList(apiKey);
                emitter.onSuccess(response);

            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
