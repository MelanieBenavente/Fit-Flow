package app.fit.fitndflow.ui.features.common.activity;

import android.os.Bundle;

import com.fit.fitndflow.R;

import app.fit.fitndflow.ui.features.home.fragment.HomeFragment;

public class MainActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.MainActContainer, new HomeFragment()).commit();
    }

    @Override
    public int getResLayout() {
        return R.layout.activity_main;
    }
}