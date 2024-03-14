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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddSerieTrainingViewModel : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    private val fitnFlowRepository: FitnFlowRepository = FitnFlowRepositoryImpl.getInstance()

    fun addNewSerie(context: Context, reps: Int, kg: Double, idExercise: Int) {
        val addSerieUseCase = AddSerieUseCase(fitnFlowRepository, context)
        val params = AddSerieUseCaseParams(reps, kg, idExercise)
        viewModelScope.launch {
            addSerieUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect { _state.emit(State.SeriesChangedInExerciseDetail(it, true)) }
        }
    }

    fun modifySerie(context: Context, serieId: Int, reps: Int, weight: Double, exerciseId: Int) {
        val modifyTrainingUseCase = ModifyTrainingUseCase(fitnFlowRepository, context)
        val params = ModifySerieUseCaseParams(serieId, reps, weight)
        viewModelScope.launch {
            modifyTrainingUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect { _state.emit(State.SeriesChangedInExerciseDetail(it, true)) }
        }
    }

    fun getSerieListOfExerciseAdded(exerciseId: Int) {
        val getSerieAddedUseCase = GetSerieAddedUseCase(fitnFlowRepository)
        val params = GetSerieAddedParam(exerciseId)
        viewModelScope.launch {
            getSerieAddedUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.FullScreenError) }
                .collect { _state.emit(State.SeriesChangedInExerciseDetail(it, false)) }
        }
    }

    fun deleteSerie(context: Context, serieId: Int) {
        val deleteSerieUseCase = DeleteSerieUseCase(fitnFlowRepository, context)
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


