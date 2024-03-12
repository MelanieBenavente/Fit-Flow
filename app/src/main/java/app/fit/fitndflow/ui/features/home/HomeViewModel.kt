package app.fit.fitndflow.ui.features.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import app.fit.fitndflow.domain.Utils
import app.fit.fitndflow.domain.common.arq.FitRxObserver
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.model.UserModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.GetTrainingUseCase
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class HomeViewModel : ViewModel() {
    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()
    private var date: Date = Date()
    private val fitnFlowRepository: FitnFlowRepository = FitnFlowRepositoryImpl.getInstance()

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

    fun emitDate() {
        viewModelScope.launch { _state.emit(State.CurrentDateChanged(date)) }
    }

    fun requestRegisterEmptyUser(context: Context) {
        val emptyUserModel = UserModel(null, null, null, null)
        RegisterUserUseCase(emptyUserModel, context, fitnFlowRepository).execute(object :
            FitRxObserver<UserModel>() {
            override fun onStart() {
                super.onStart()
                viewModelScope.launch {
                    _state.emit(State.Loading)
                }
            }

            override fun onSuccess(apiRegisterResponse: UserModel) {
                viewModelScope.launch {
                    _state.emit(State.RegisterCompleted)
                }
            }

            override fun onError(e: Throwable) {
                viewModelScope.launch {
                    _state.emit(State.FullScreenError)
                }
            }
        })
    }

    fun requestTrainingFromModel(context: Context) {
        val date: String = Utils.getEnglishFormatDate(date)
        val getTrainingUseCase = GetTrainingUseCase(context, date, fitnFlowRepository)
        getTrainingUseCase.execute(object : FitRxObserver<List<CategoryModel>>() {

            override fun onStart() {
                super.onStart()
                viewModelScope.launch {
                    _state.emit(State.Loading)
                }
            }

            override fun onSuccess(categoryModelList: List<CategoryModel>) {
                viewModelScope.launch {
                    _state.emit(State.TrainingListRecived(categoryModelList))
                }
            }

            override fun onError(e: Throwable) {
                viewModelScope.launch {
                    _state.emit(State.FullScreenError)
                }
            }
        })
    }
}

sealed class State {
    object Loading : State()
    object RegisterCompleted : State()
    object FullScreenError : State()
    data class CurrentDateChanged(val date: Date) : State()
    data class TrainingListRecived(val categoryList: List<CategoryModel>) : State()
}