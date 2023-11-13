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

public class MainActivity extends CommonActivity {
    private static final int REQUEST_CODE = 345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.MainActContainer, new HomeFragment()).commit();

        MyNotificationManager myNotificationManager = new MyNotificationManager();
        myNotificationManager.scheduleNotification(this, 6000);
    }

    @Override
    public int getResLayout() {
        return R.layout.activity_main;
    }

    public Boolean checkNotificationPermission() {

        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            Toast.makeText(this, "DONT HAVE A PERMISSION", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, REQUEST_CODE);
        }
    }
}





