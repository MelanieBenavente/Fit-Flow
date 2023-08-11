package app.fit.fitndflow.ui.features.common;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.fit.fitndflow.R;


public abstract class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResLayout());
    }

    public abstract @LayoutRes int getResLayout();

    public void nextFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.MainActContainer, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }
}
