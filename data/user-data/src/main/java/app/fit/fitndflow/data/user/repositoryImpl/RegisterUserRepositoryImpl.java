package app.fit.fitndflow.data.user.repositoryImpl;

import android.content.Context;

import app.fit.fitndflow.data.common.datasource.SharedPrefsLocalDataSource;

import com.fit.fitndflow.app.domain.user.repository.RegisterUserRepository;

public class RegisterUserRepositoryImpl implements RegisterUserRepository {
    private Context mContext;
    private SharedPrefsLocalDataSource sharedPrefsLocalDataSource;

    public RegisterUserRepositoryImpl(Context context, SharedPrefsLocalDataSource sharedPrefsLocalDataSource) {
        this.mContext = context;
        this.sharedPrefsLocalDataSource = sharedPrefsLocalDataSource;
    }

    @Override
    public String getApikey() throws Exception {
        return sharedPrefsLocalDataSource.getApiKey();
    }
}

