package com.fit.fitndflow.data.repository;

import android.content.Context;

import com.fit.fitndflow.data.common.ApiInterface;
import com.fit.fitndflow.data.common.SharedPrefs;
import com.fit.fitndflow.data.common.model.ExcepcionApi;
import com.fit.fitndflow.data.datasource.CategoriesAndExercisesLocalDataSource;
import com.fit.fitndflow.data.datasource.TrainingLocalDataSource;
import com.fit.fitndflow.data.dto.UserDto;
import com.fit.fitndflow.data.dto.categories.CategoryDto;
import com.fit.fitndflow.data.dto.mapper.CategoryModelMapperKt;
import com.fit.fitndflow.data.dto.mapper.UserModelMapperKt;

import java.util.List;

import app.fit.fitndflow.domain.model.CategoryModel;
import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.repository.FitnFlowRepository;
import retrofit2.Response;

public class FitnFlowRepositoryImpl implements FitnFlowRepository {
    private Context mContext;
    private ApiInterface apiInterface;


    public FitnFlowRepositoryImpl(Context context, ApiInterface apiInterface) {
        this.mContext = context;
        this.apiInterface = apiInterface;
    }

    @Override
    public UserModel registerUser(String userName, String email, String premium) throws Exception {
        UserDto userDto = new UserDto(userName, email, premium, null);

        Response<UserDto> response;
        try {
            response = apiInterface.register(userDto).execute();
            if (response != null && !response.isSuccessful()) {
                throw new ExcepcionApi(response.code()); //si no ha ido bien la llamada: server no devuelve un 200
            }
            if (response != null && response.body() != null) {
                UserModel userModelMapped = UserModelMapperKt.toModel(response.body());
                SharedPrefs.saveApikeyToSharedPRefs(mContext, userModelMapped.getApiKey());
                return userModelMapped;
            } else {
                throw new Exception("Error register"); //si la respuesta es nula
            }
        } catch (Exception e) {
            //solamente salta cuando hay un error de mapeo (ej. server me devuelve un json diferente al esperado)
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}

