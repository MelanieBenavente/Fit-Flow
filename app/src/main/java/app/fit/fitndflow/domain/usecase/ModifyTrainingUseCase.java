package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.dto.trainings.SerieDto;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class ModifyTrainingUseCase extends FitUseCase<SerieDto, List<SerieModel>> {
    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public ModifyTrainingUseCase(Context context, int serieId, int reps, double weight, FitnFlowRepository fitnFlowRepository){
        super(createNewSerieDto(serieId, reps, weight));
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }
    @Override
    public Single<List<SerieModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try{
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<SerieModel> response = fitnFlowRepository.modifySerie(inputParams, apiKey);
                emitter.onSuccess(response);
            }catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
    private static SerieDto createNewSerieDto (int serieId, int reps, double weight){
        SerieDto serieDto = new SerieDto(serieId, reps, weight);
        return serieDto;
    }
}
