package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class DeleteSerieUseCase extends FitUseCase<Integer, List<SerieModel>> {
    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public DeleteSerieUseCase(int inputParams, Context context, FitnFlowRepository fitnFlowRepository){
        super(inputParams);
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }
    @Override
    public Single<List<SerieModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try{
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<SerieModel> response = fitnFlowRepository.deleteSerie(inputParams, apiKey);
                emitter.onSuccess(response);
            }catch (Exception e){
                emitter.onError(e);
            }
        });
    }
}
