package app.fit.fitndflow.ui.features.common;


import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fit.fitndflow.R;

import app.fit.fitndflow.ui.features.common.notification.MyNotificationManager;
import app.fit.fitndflow.ui.features.home.HomeFragment;
import android.view.Window;

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





