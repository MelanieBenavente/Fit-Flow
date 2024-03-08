package app.fit.fitndflow.ui.features.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl
import app.fit.fitndflow.domain.Utils
import app.fit.fitndflow.domain.common.arq.FitRxObserver
import app.fit.fitndflow.domain.model.CategoryModel
import app.fit.fitndflow.domain.model.SerieModel
import app.fit.fitndflow.domain.model.UserModel
import app.fit.fitndflow.domain.repository.FitnFlowRepository
import app.fit.fitndflow.domain.usecase.AddSerieUseCase
import app.fit.fitndflow.domain.usecase.DeleteSerieUseCase
import app.fit.fitndflow.domain.usecase.GetSerieAddedUseCase
import app.fit.fitndflow.domain.usecase.GetTrainingUseCase
import app.fit.fitndflow.domain.usecase.ModifyTrainingUseCase
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase
import java.util.Calendar
import java.util.Date

class HomeViewModel : ViewModel() {

    val fitnFlowRepository: FitnFlowRepository = FitnFlowRepositoryImpl.getInstance()
    val mutableActualDate: MutableLiveData<Date> = MutableLiveData(Date())
    val mutableSlideError: MutableLiveData<Boolean> = MutableLiveData(false)
    val mutableFullScreenError: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSaveSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val dailyTrainingMutableList: MutableLiveData<List<CategoryModel>> = MutableLiveData()
    val hashmapMutableSerieListByExerciseId: MutableLiveData<HashMap<Int, List<SerieModel>>?> =
        MutableLiveData(HashMap())


    fun dayBefore() {
        var date: Date = mutableActualDate.value!!
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        date = calendar.time
        mutableActualDate.value = date
    }

    fun dayAfter() {
        var date: Date = mutableActualDate.value!!
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, +1)
        date = calendar.time
        mutableActualDate.value = date
    }

    fun requestRegisterEmptyUser(context: Context) {
        val emptyUserModel = UserModel(null, null, null, null)
        RegisterUserUseCase(emptyUserModel, context, fitnFlowRepository).execute(object :
            FitRxObserver<UserModel>() {
            override fun onStart() {
                super.onStart()
                isLoading.value = true
            }

            override fun onSuccess(apiRegisterResponse: UserModel) {
                isLoading.value = false
                mutableFullScreenError.value = false
            }

            override fun onError(e: Throwable) {
                isLoading.value = false
                mutableFullScreenError.value = true
            }
        })
    }

    fun requestTrainingFromModel(context: Context) {
        val date: String = Utils.getEnglishFormatDate(mutableActualDate.value)
        val getTrainingUseCase = GetTrainingUseCase(context, date, fitnFlowRepository)
        getTrainingUseCase.execute(object : FitRxObserver<List<CategoryModel>>() {

            override fun onStart() {
                super.onStart()
                mutableFullScreenError.value = false
                isLoading.value = true
            }

            override fun onSuccess(categoryModelList: List<CategoryModel>) {
                dailyTrainingMutableList.value = categoryModelList
                isLoading.value = false
                mutableFullScreenError.value = false
            }

            override fun onError(e: Throwable) {
                isLoading.value = false
                mutableFullScreenError.value = true
            }
        })
    }

    fun addNewSerie(context: Context, reps: Int, kg: Double, idExercise: Int) {
        AddSerieUseCase(context, Utils.getEnglishFormatDate(mutableActualDate.value), reps,
            kg, idExercise, fitnFlowRepository).execute(object : FitRxObserver<List<SerieModel>>() {

            override fun onStart() {
                super.onStart()
                isLoading.value = true
                isSaveSuccess.value = false
                mutableSlideError.value = false
            }
            override fun onSuccess(serieModelList: List<SerieModel>) {
                val actualHashMap: HashMap<Int, List<SerieModel>>? = hashmapMutableSerieListByExerciseId.value
                actualHashMap?.put(idExercise, serieModelList)
                hashmapMutableSerieListByExerciseId.value = actualHashMap
                isSaveSuccess.value = true
                isLoading.value = false
                mutableFullScreenError.value = false
            }
            override fun onError(e: Throwable) {
                isLoading.value = false
                isSaveSuccess.value = false
                mutableSlideError.value = true
            }
        })
    }

    fun modifySerie(context: Context, serieId: Int, reps: Int, weight: Double, exerciseId: Int) {
        ModifyTrainingUseCase(context, serieId, reps, weight, fitnFlowRepository).
        execute(object: FitRxObserver<List<SerieModel>>() {

            override fun onStart() {
                super.onStart()
                isLoading.value = true
                isSaveSuccess.value = false
                mutableSlideError.value = false
            }
            override fun onSuccess(serieModelList: List<SerieModel>) {
                val actualHashMap: HashMap<Int, List<SerieModel>>? = hashmapMutableSerieListByExerciseId.value
                actualHashMap?.put(exerciseId, serieModelList)
                hashmapMutableSerieListByExerciseId.value = actualHashMap
                isSaveSuccess.value = true
                isLoading.value = false
                mutableFullScreenError.value = false
            }
            override fun onError(e: Throwable) {
                isLoading.value = false
                isSaveSuccess.value = false
                mutableSlideError.value = true
            }
        })
    }

    fun getSerieListOfExerciseAdded(exerciseId: Int) {
        GetSerieAddedUseCase(exerciseId, Utils.getEnglishFormatDate(mutableActualDate.value), fitnFlowRepository).
        execute(object: FitRxObserver<List<SerieModel>>() {

            override fun onSuccess(serieModelList: List<SerieModel>) {
                val actualHashMap: HashMap<Int, List<SerieModel>>? = hashmapMutableSerieListByExerciseId.value
                actualHashMap?.put(exerciseId, serieModelList)
                hashmapMutableSerieListByExerciseId.value = actualHashMap
            }
            override fun onError(e: Throwable) {
                TODO("Nothing to do")
            }
        })
    }

    fun deleteSerie(serieId: Int, exerciseId: Int, context: Context) {
        DeleteSerieUseCase(serieId, context, fitnFlowRepository).execute(object: FitRxObserver<List<SerieModel>>() {

            override fun onStart() {
                super.onStart()
                isLoading.value = true
                mutableSlideError.value = false
            }
            override fun onSuccess(serieModelList: List<SerieModel>) {
                val actualHashMap: HashMap<Int, List<SerieModel>>? = hashmapMutableSerieListByExerciseId.value
                actualHashMap?.put(exerciseId, serieModelList)
                hashmapMutableSerieListByExerciseId.value = actualHashMap
                isLoading.value = false
                mutableSlideError.value = false
            }

            override fun onError(e: Throwable) {
                mutableSlideError.value = true
                isLoading.value = false
            }
        })
    }
}