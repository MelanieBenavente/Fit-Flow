package app.fit.fitndflow.ui.features.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.fitndflow.app.domain.common.models.ExerciseModel
import com.fit.fitndflow.app.domain.exercises.exercisesUseCases.AddExerciseUseCase
import com.fit.fitndflow.app.domain.exercises.exercisesUseCases.AddExerciseUseCaseParams
import com.fit.fitndflow.app.domain.exercises.exercisesUseCases.DeleteExerciseUseCase
import com.fit.fitndflow.app.domain.exercises.exercisesUseCases.ExerciseModelInLanguages
import com.fit.fitndflow.app.domain.exercises.exercisesUseCases.ExerciseToDeleteParams
import com.fit.fitndflow.app.domain.exercises.exercisesUseCases.ModifyExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val addExerciseUseCase: AddExerciseUseCase,
    private val modifyExerciseUseCase: ModifyExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase

) : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    var lastName: String? = null

    fun addNewExercise(language: String, nameExercise: String, categoryId: Int){
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
    fun modifyExercise(nameExercise: String, language: String, exerciseId: Int, categoryId: Int){
        val params = ExerciseModelInLanguages(exerciseId, nameExercise, language, categoryId)
        viewModelScope.launch {
            modifyExerciseUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.SlideError) }
                .collect{ _state.emit(State.ExerciseListRecived(it))
                }
        }
    }

    fun deleteExercise(exerciseId: Int){
        val params = ExerciseToDeleteParams(exerciseId)
        viewModelScope.launch {
            deleteExerciseUseCase(params)
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