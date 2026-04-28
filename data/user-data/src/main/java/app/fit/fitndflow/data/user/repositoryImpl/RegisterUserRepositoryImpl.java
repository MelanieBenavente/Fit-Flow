package app.fit.fitndflow.data.user.repositoryImpl;

import com.fit.fitndflow.app.domain.user.repository.RegisterUserRepository;

import app.fit.fitndflow.data.common.datasource.local.SharedPrefsLocalDataSource;

public class RegisterUserRepositoryImpl implements RegisterUserRepository {
    private SharedPrefsLocalDataSource sharedPrefsLocalDataSource;

    public RegisterUserRepositoryImpl(SharedPrefsLocalDataSource sharedPrefsLocalDataSource) {
        this.sharedPrefsLocalDataSource = sharedPrefsLocalDataSource;
    }

    @Override
    public String getApikey() throws Exception {
        return sharedPrefsLocalDataSource.getApiKey();
    }
}

