package app.fit.fitndflow.ui.features.common;


import android.os.Bundle;

import com.fit.fitndflow.R;

import app.fit.fitndflow.ui.features.home.HomeFragment;

public class MainActivity extends CommonActivity {
    private static final int REQUEST_CODE = 345;

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





