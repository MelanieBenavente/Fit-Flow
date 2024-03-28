package app.fit.fitndflow.ui.features.training

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.AddSerieUseCase
import app.fit.fitndflow.domain.usecase.AddSerieUseCaseParams
import app.fit.fitndflow.domain.usecase.DeleteSerieUseCase
import app.fit.fitndflow.domain.usecase.GetSerieAddedParam
import app.fit.fitndflow.domain.usecase.GetSerieAddedUseCase
import app.fit.fitndflow.domain.usecase.GetSerieToDeleteParams
import app.fit.fitndflow.domain.usecase.ModifySerieUseCaseParams
import app.fit.fitndflow.domain.usecase.ModifyTrainingUseCase
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


    fun addNewSerie(reps: Int, kg: Double, idExercise: Int) {
        val params = AddSerieUseCaseParams(reps, kg, idExercise)
        viewModelScope.launch {
            addSerieUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect { _state.emit(State.SeriesChangedInExerciseDetail(it, true)) }
        }
    }

    fun modifySerie(serieId: Int, reps: Int, weight: Double, exerciseId: Int) {
        val params = ModifySerieUseCaseParams(serieId, reps, weight)
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
                .collect { _state.emit(State.SeriesChangedInExerciseDetail(it, false)) }
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
        data class SeriesChangedInExerciseDetail(
            val serieList: List<SerieModel>,
            val showSlideSuccess: Boolean
        ) : State()
    }
}


