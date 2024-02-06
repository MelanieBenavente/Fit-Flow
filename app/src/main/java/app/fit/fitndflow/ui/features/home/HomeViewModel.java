package app.fit.fitndflow.ui.features.home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.fit.fitndflow.data.repository.FitnFlowRepositoryImpl;
import app.fit.fitndflow.domain.Utils;
import app.fit.fitndflow.domain.common.arq.FitObserver;
import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import app.fit.fitndflow.domain.usecase.AddSerieUseCase;
import app.fit.fitndflow.domain.usecase.DeleteSerieUseCase;
import app.fit.fitndflow.domain.usecase.GetSerieAddedUseCase;
import app.fit.fitndflow.domain.usecase.GetTrainingUseCase;
import app.fit.fitndflow.domain.usecase.ModifyTrainingUseCase;
import app.fit.fitndflow.domain.usecase.RegisterUserUseCase;

public class HomeViewModel extends ViewModel {

    private FitnFlowRepository fitnFlowRepository = FitnFlowRepositoryImpl.getInstance();
    private MutableLiveData<Date> mutableActualDate = new MutableLiveData<>(new Date());
    private MutableLiveData<Boolean> mutableSlideError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> mutableFullScreenError = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isSaveSuccess = new MutableLiveData<>(false);
    private MutableLiveData<List<CategoryModel>> dailyTrainingMutableList = new MutableLiveData<>();
    private MutableLiveData<HashMap<Integer, List<SerieModel>>> hashmapMutableSerieListByExerciseId = new MutableLiveData<>(new HashMap<>());

    /*
    getters
     */
    public MutableLiveData<Date> getMutableActualDate() {
        return mutableActualDate;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public MutableLiveData<List<CategoryModel>> getDailyTrainingMutableList() {
        return dailyTrainingMutableList;
    }
    public MutableLiveData<Boolean> getMutableSlideError() {
        return mutableSlideError;
    }

    public MutableLiveData<Boolean> getIsSaveSuccess() {return isSaveSuccess; }

    public MutableLiveData<Boolean> getMutableFullScreenError() {
        return mutableFullScreenError;
    }


    public MutableLiveData<HashMap<Integer, List<SerieModel>>> getHashmapMutableSerieListByExerciseId() {
        return hashmapMutableSerieListByExerciseId;
    }
    //end getters

    public void dayBefore(){
        Date date = mutableActualDate.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        // Obtener la nueva fecha
        date = calendar.getTime();
        mutableActualDate.setValue(date);
    }
    public void dayAfter(){
        Date date = mutableActualDate.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +1);

        date = calendar.getTime();
        mutableActualDate.setValue(date);
    }

    public void requestRegisterEmptyUser(Context context) {
        UserModel emptyUserModel = new UserModel(null, null, null, null);
        new RegisterUserUseCase(emptyUserModel, context, fitnFlowRepository).execute(new FitObserver<UserModel>() {

            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
            }
            @Override
            public void onSuccess(UserModel apiRegisterResponse) {
                isLoading.setValue(false);
                mutableFullScreenError.setValue(false);

            }

            @Override
            public void onError(Throwable e) {
                isLoading.setValue(false);
                mutableFullScreenError.setValue(true);
            }
        });
    }

    public void requestTrainingFromModel(Context context){
        String date = Utils.getEnglishFormatDate(mutableActualDate.getValue());
        GetTrainingUseCase getTrainingUseCase = new GetTrainingUseCase(context, date, fitnFlowRepository);

        getTrainingUseCase.execute(new FitObserver<List<CategoryModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                mutableFullScreenError.setValue(false);
                isLoading.setValue(true);
            }
            @Override
            public void onSuccess(List<CategoryModel> categoryModelList) {
                dailyTrainingMutableList.setValue(categoryModelList);
                isLoading.setValue(false);
                mutableFullScreenError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                isLoading.setValue(false);
                mutableFullScreenError.setValue(true);

            }
        });
    }

    public void addNewSerie(Context context, int reps, double kg, int idExercise){
        new AddSerieUseCase(context, Utils.getEnglishFormatDate(mutableActualDate.getValue()), reps, kg, idExercise, fitnFlowRepository).execute(new FitObserver<List<SerieModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                isSaveSuccess.setValue(false);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onSuccess(List<SerieModel> serieModelList) {
                HashMap<Integer, List<SerieModel>> actualHashMap= hashmapMutableSerieListByExerciseId.getValue();
                actualHashMap.put(idExercise, serieModelList);
                hashmapMutableSerieListByExerciseId.setValue(actualHashMap);
                isSaveSuccess.setValue(true);
                isLoading.setValue(false);
                mutableFullScreenError.setValue(false);
            }
            @Override
            public void onError(Throwable e) {
                isLoading.setValue(false);
                isSaveSuccess.setValue(false);
                mutableSlideError.setValue(true);
            }
        });
    }
    public void modifySerie(Context context,int serieId, int reps, double weight, int exerciseId){
        new ModifyTrainingUseCase(context, serieId, reps, weight, fitnFlowRepository).execute(new FitObserver<List<SerieModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                isLoading.setValue(true);
                isSaveSuccess.setValue(false);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onSuccess(List<SerieModel> serieModels) {
                HashMap<Integer, List<SerieModel>> actualHashMap= hashmapMutableSerieListByExerciseId.getValue();
                actualHashMap.put(exerciseId, serieModels);
                hashmapMutableSerieListByExerciseId.setValue(actualHashMap);
                isSaveSuccess.setValue(true);
                isLoading.setValue(false);
                mutableFullScreenError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                isLoading.setValue(false);
                isSaveSuccess.setValue(false);
                mutableSlideError.setValue(true);
            }
        });
    }
    public void getSerieListOfExerciseAdded(int exerciseId){
        new GetSerieAddedUseCase(exerciseId, Utils.getEnglishFormatDate(mutableActualDate.getValue()), fitnFlowRepository).execute(new FitObserver<List<SerieModel>>() {
            @Override
            public void onSuccess(List<SerieModel> serieModels) {
                HashMap<Integer, List<SerieModel>> actualHashMap= hashmapMutableSerieListByExerciseId.getValue();
                actualHashMap.put(exerciseId, serieModels);
                hashmapMutableSerieListByExerciseId.setValue(actualHashMap);
            }

            @Override
            public void onError(Throwable e) {
                //nothing to do
            }

        });
    }
    public void deleteSerie(int serieId, int exerciseId, Context context){
        new DeleteSerieUseCase(serieId, context, fitnFlowRepository).execute(new FitObserver<List<SerieModel>>() {
            @Override
            protected void onStart(){
                super.onStart();
                isLoading.setValue(true);
                mutableSlideError.setValue(false);
            }
            @Override
            public void onSuccess(List<SerieModel> serieModels) {
                HashMap<Integer, List<SerieModel>> actualHashMap= hashmapMutableSerieListByExerciseId.getValue();
                actualHashMap.put(exerciseId, serieModels);
                hashmapMutableSerieListByExerciseId.setValue(actualHashMap);
                isLoading.setValue(false);
                mutableSlideError.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                mutableSlideError.setValue(true);
                isLoading.setValue(false);
            }
        });
    }
}
