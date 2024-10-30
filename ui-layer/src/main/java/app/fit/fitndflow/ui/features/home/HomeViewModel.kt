package app.fit.fitndflow.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit.fitndflow.app.domain.common.models.CategoryModel
import com.fit.fitndflow.app.domain.common.utils.Utils
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.GetTrainingUseCaseParams
import com.fit.fitndflow.app.domain.user.userUseCases.RegisterUserUseCaseParams
import com.fit.fitndflow.app.domain.trainings.TrainingUseCases.GetTrainingUseCase
import com.fit.fitndflow.app.domain.user.userUseCases.GetIsUserRegisteredUseCase
import com.fit.fitndflow.app.domain.user.userUseCases.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val getTrainingUseCase: GetTrainingUseCase,
    private val getIsUserRegisteredUseCase: GetIsUserRegisteredUseCase

) : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    private var date: Date = Date()

    fun dayBefore() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        date = calendar.time
        viewModelScope.launch {
            _state.emit(State.CurrentDateChanged(date))
        }
    }

    fun dayAfter() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, +1)
        date = calendar.time
        viewModelScope.launch {
            _state.emit(State.CurrentDateChanged(date))
        }
    }

    fun isUserRegistered() = getIsUserRegisteredUseCase.isUserRegistered()


    fun emitDate() {
        viewModelScope.launch { _state.emit(State.CurrentDateChanged(date)) }
    }

    fun requestRegisterEmptyUser() {
        val params = RegisterUserUseCaseParams()
        viewModelScope.launch {
            registerUserUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.FullScreenError) }
                .collect { _state.emit(State.RegisterCompleted) }
        }
    }


    fun requestTrainingFromModel() {
        val date: String = Utils.getEnglishFormatDate(date)
        val params = GetTrainingUseCaseParams(date)
        viewModelScope.launch {
            getTrainingUseCase(params)
                .onStart { _state.emit(State.Loading) }
                .catch { _state.emit(State.FullScreenError) }
                .collect{_state.emit(State.TrainingListRecived(it))}
        }
    }
}

sealed class State {
    object Loading : State()
    object RegisterCompleted : State()
    object FullScreenError : State()
    data class CurrentDateChanged(val date: Date) : State()
    data class TrainingListRecived(val categoryList: List<CategoryModel>) : State()
}