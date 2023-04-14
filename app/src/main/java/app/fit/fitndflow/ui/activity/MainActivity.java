package app.fit.fitndflow.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fit.fitndflow.R;

import app.fit.fitndflow.ui.fragment.MainListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.MainActContainer, new MainListFragment()).commit();

    }
}