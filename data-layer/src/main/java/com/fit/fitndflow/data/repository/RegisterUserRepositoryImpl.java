package com.fit.fitndflow.data.repository;

import android.content.Context;

import com.fit.fitndflow.data.common.ApiInterface;
import com.fit.fitndflow.data.common.model.ExcepcionApi;
import com.fit.fitndflow.data.datasource.SharedPrefsLocalDataSource;
import com.fit.fitndflow.data.dto.UserDto;
import com.fit.fitndflow.data.dto.mapper.UserModelMapperKt;

import app.fit.fitndflow.domain.model.UserModel;
import app.fit.fitndflow.domain.repository.RegisterUserRepository;
import retrofit2.Response;

public class RegisterUserRepositoryImpl implements RegisterUserRepository {
    private Context mContext;
    private ApiInterface apiInterface;
    private SharedPrefsLocalDataSource sharedPrefsLocalDataSource;

    public RegisterUserRepositoryImpl(Context context, ApiInterface apiInterface, SharedPrefsLocalDataSource sharedPrefsLocalDataSource) {
        this.mContext = context;
        this.apiInterface = apiInterface;
        this.sharedPrefsLocalDataSource = sharedPrefsLocalDataSource;
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
                String apiKey = userModelMapped.getApiKey();
                sharedPrefsLocalDataSource.saveApiKey(apiKey);
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

    @Override
    public String getApikey() throws Exception {
        return sharedPrefsLocalDataSource.getApiKey();
    }
}

