package app.fit.fitndflow.ui.features.training

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import app.fit.fitndflow.domain.common.arq.FitRxObserver
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.AddSerieUseCase
import app.fit.fitndflow.domain.usecase.AddSerieUseCaseParams
import app.fit.fitndflow.domain.usecase.DeleteSerieUseCase
import app.fit.fitndflow.domain.usecase.GetSerieAddedParam
import app.fit.fitndflow.domain.usecase.GetSerieAddedUseCase
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
        ModifyTrainingUseCase(context, serieId, reps, weight, fitnFlowRepository).execute(object :
            FitRxObserver<List<SerieModel>>() {

            override fun onStart() {
                super.onStart()
                viewModelScope.launch {
                    _state.emit(State.Loading)
                }
            }

            override fun onSuccess(serieModelList: List<SerieModel>) {
                viewModelScope.launch {
                    _state.emit(State.SeriesChangedInExerciseDetail(serieModelList, true))
                }
            }

            override fun onError(e: Throwable) {
                viewModelScope.launch {
                    _state.emit(State.SlideError)
                }
            }
        })
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

        fun deleteSerie(serieId: Int, exerciseId: Int, context: Context) {
            DeleteSerieUseCase(serieId, context, fitnFlowRepository).execute(object :
                FitRxObserver<List<SerieModel>>() {

                override fun onStart() {
                    super.onStart()
                    viewModelScope.launch {
                        _state.emit(State.Loading)
                    }
                }

                override fun onSuccess(serieModelList: List<SerieModel>) {
                    viewModelScope.launch {
                        _state.emit(State.SeriesChangedInExerciseDetail(serieModelList, true))
                    }
                }

                override fun onError(e: Throwable) {
                    viewModelScope.launch {
                        _state.emit(State.SlideError)
                    }
                }
            })
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


