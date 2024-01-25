package app.fit.fitndflow.domain.usecase;

import android.content.Context;

import java.util.List;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.data.dto.exercises.ExerciseDto;
import app.fit.fitndflow.data.dto.trainings.AddSerieRequestDto;
import app.fit.fitndflow.data.dto.trainings.SerieForAddSerieRequestDto;
import app.fit.fitndflow.domain.common.usecase.FitUseCase;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import io.reactivex.Single;

public class AddSerieUseCase extends FitUseCase<AddSerieRequestDto, List<SerieModel>> {
    private FitnFlowRepository fitnFlowRepository;
    private Context context;

    public AddSerieUseCase(Context context, String date, int reps, double kg, int idExercise, FitnFlowRepository fitnFlowRepository){
        super(new AddSerieRequestDto(date, new SerieForAddSerieRequestDto(reps, kg, new ExerciseDto(idExercise, null))));
        this.context = context;
        this.fitnFlowRepository = fitnFlowRepository;
    }
    @Override
    public Single<List<SerieModel>> buildUseCaseObservable() {
        return Single.create(emitter -> {
            try{
                String apiKey = SharedPrefs.getApikeyFromSharedPRefs(context);
                List<SerieModel> response = fitnFlowRepository.addNewSerie(inputParams, apiKey);
                emitter.onSuccess(response);
            }catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }
}
