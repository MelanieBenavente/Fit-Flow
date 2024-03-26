package app.fit.fitndflow.ui.features.exercises

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import app.fit.fitndflow.domain.model.ExerciseModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.AddExerciseUseCase
import app.fit.fitndflow.domain.usecase.AddExerciseUseCaseParams
import app.fit.fitndflow.domain.usecase.ExerciseModelInLanguages
import app.fit.fitndflow.domain.usecase.ModifyExerciseUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ExercisesViewModel : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    var lastName: String? = null
    private val fitnFlowRepository: FitnFlowRepository = FitnFlowRepositoryImpl.getInstance()

    fun addNewExercise(context: Context, language: String, nameExercise: String, categoryId: Int){
        val addExerciseUseCase = AddExerciseUseCase(fitnFlowRepository, context)
        val params = AddExerciseUseCaseParams(nameExercise, language, categoryId)
        viewModelScope.launch {
            addExerciseUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch {
                    lastName = nameExercise
                    _state.emit(State.SlideError)
                }
                .collect {
                    lastName = null
                    _state.emit(State.ExerciseListRecived(it))
                }
        }
    }
    fun modifyExercise(context: Context, nameExercise: String, language: String, exerciseId: Int, categoryId: Int){
        val modifyExerciseUseCase = ModifyExerciseUseCase(fitnFlowRepository, context)
        val params = ExerciseModelInLanguages(exerciseId, nameExercise, language, categoryId)
        viewModelScope.launch {
            modifyExerciseUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect{ _state.emit(State.ExerciseListRecived(it))
                }
        }
    }


    sealed class State {
        object Loading : State()
        object SlideError : State()
        data class ExerciseListRecived(
            val exerciseList: List<ExerciseModel>
        ) : State()
    }
}