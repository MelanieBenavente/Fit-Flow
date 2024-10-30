package app.fit.fitndflow.ui.features.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.fitndflow.app.domain.common.models.SerieModel
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.AddSerieUseCase
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.AddSerieUseCaseParams
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.DeleteSerieUseCase
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.GetSerieAddedParam
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.GetSerieAddedUseCase
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.GetSerieToDeleteParams
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.ModifySerieUseCaseParams
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.ModifyTrainingUseCase
import com.fit.fitndflow.app.domain.trainings.model.SerieInfoWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSerieTrainingViewModel @Inject constructor(
    private val addSerieUseCase: AddSerieUseCase,
    private val modifyTrainingUseCase: ModifyTrainingUseCase,
    private val getSerieAddedUseCase: GetSerieAddedUseCase,
    private val deleteSerieUseCase: DeleteSerieUseCase

) : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()


    fun addNewSerie(reps: Int, kg: Double, idExercise: Int, record: SerieModel?) {
        val params = AddSerieUseCaseParams(reps, kg, idExercise, record)
        viewModelScope.launch {
            addSerieUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect { _state.emit(State.SeriesChangedInExerciseDetail(it,
                    showLastSerieAdded = true
                )) }
        }
    }

    fun modifySerie(serieId: Int, reps: Int, weight: Double, record: SerieModel?) {
        val params = ModifySerieUseCaseParams(serieId, reps, weight, record)
        viewModelScope.launch {
            modifyTrainingUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect { _state.emit(State.SeriesChangedInExerciseDetail(it, true)) }
        }
    }

    fun getSerieListOfExerciseAdded(exerciseId: Int) {
        val params = GetSerieAddedParam(exerciseId)
        viewModelScope.launch {
            getSerieAddedUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.FullScreenError) }
                .collect { _state.emit(State.SerieListRecived(it)) }
        }
    }

    fun deleteSerie(serieId: Int) {
        val params = GetSerieToDeleteParams(serieId)
        viewModelScope.launch {
            deleteSerieUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect { _state.emit(State.SeriesChangedInExerciseDetail(it, true)) }
        }
    }

    sealed class State {
        object Loading : State()
        object FullScreenError : State()
        object SlideError : State()
        data class SerieListRecived(
            val serieList: List<SerieModel>
        ) : State()
        data class SeriesChangedInExerciseDetail(
            val serieInfoWrapper: SerieInfoWrapper,
            val showLastSerieAdded: Boolean = false
        ) : State()
    }
}


