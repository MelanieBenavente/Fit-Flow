package app.fit.fitndflow.domain.usecase;

import java.util.List;

import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.GetSerieAddedModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class GetSerieAddedUseCase extends FitUseCase<GetSerieAddedModel, List<SerieModel>> {
    private FitnFlowRepository fitnFlowRepository;

    public GetSerieAddedUseCase(int idExercise, String date, FitnFlowRepository fitnFlowRepository){
        super(new GetSerieAddedModel(idExercise, date));
        this.fitnFlowRepository = fitnFlowRepository;
    }
    @Override
    public Single<List<SerieModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try{
                List<SerieModel> response = fitnFlowRepository.getSerieListOfExerciseAdded(inputParams.getDate(), inputParams.getExerciseId());
                emitter.onSuccess(response);
            } catch (Exception e){
                emitter.onError(e);
            }
        });
    }
}
