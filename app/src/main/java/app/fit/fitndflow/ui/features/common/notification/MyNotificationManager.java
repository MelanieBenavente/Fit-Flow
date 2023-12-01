package app.fit.fitndflow.ui.features.common.notification;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.fit.fitndflow.R;

import app.fit.fitndflow.data.common.SharedPrefs;
import app.fit.fitndflow.ui.features.categories.ConfirmationDialogFragment;
import app.fit.fitndflow.ui.features.common.MainActivity;

public class MyNotificationManager extends BroadcastReceiver {
    private static final int REQUEST_CODE_TO_PRINT_NOTIFICATION = 1;
    private static final int REQUEST_CODE_PERMISSION = 123;
    public static final int TRAINING_TYPE = 2;

    @Override
    public void onReceive(Context context, Intent intent) {

        printNotification(context, intent);
    }

    private static void printNotification(Context context, Intent intent) {
        int type = intent.getIntExtra("type", -1);
        Class activityClass = getNotificationType(type);

        Intent notificationIntent = new Intent(context, activityClass);
        PendingIntent contentIntent = PendingIntent.getActivity(context, REQUEST_CODE_TO_PRINT_NOTIFICATION, notificationIntent, PendingIntent.FLAG_MUTABLE);

        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("text");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "Channel name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        int notificationId = 1;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationBuilder.setSmallIcon(R.drawable.splashscreen_fitndflow);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        }

        Notification notification = notificationBuilder.build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);


        try {
            manager.notify(notificationId, notification);
        } catch (SecurityException e) {
            Log.e("Notification", e.getMessage());
        }
    }

    private static Class getNotificationType(int type) {
        switch (type) {
            case TRAINING_TYPE:
                return MainActivity.class;
            default:
                return MainActivity.class;
        }
    }
    public static String getNotificationTitle(int type, Context context){
        switch(type) {
            case TRAINING_TYPE:
                return context.getString(R.string.notification_title);
            default:
                return context.getString(R.string.notification_title);
        }
    }

    public static String getNotificationBody(int type, Context context){
        switch(type) {
            case TRAINING_TYPE:
                return context.getString(R.string.notification_body);
            default:
                return context.getString(R.string.notification_body);
        }
    }



    public static void scheduleNotification(Activity activity, long delayMillis, int type) {
        if (checkNotificationPermission(activity)) {

            Intent intent = new Intent(activity, MyNotificationManager.class);
            intent.putExtra("title", getNotificationTitle(type, activity));
            intent.putExtra("text", getNotificationBody(type, activity));
            intent.putExtra("type", type);
            int unicId = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, unicId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);


            AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayMillis, pendingIntent);
        }
    }

    public static void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(activity, new String[]{POST_NOTIFICATIONS}, REQUEST_CODE_PERMISSION);
        }
    }

    private static Boolean checkNotificationPermission(Activity activity) {
            boolean hasPermission = NotificationManagerCompat.from(activity).areNotificationsEnabled();
            return hasPermission;
    }

    public static void askForPermissions(Fragment fragment){
        if(!checkNotificationPermission(fragment.requireActivity()) && !SharedPrefs.get(fragment.requireActivity()).isNotificationShown()
                && !fragment.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){

            SharedPrefs.get(fragment.requireActivity()).saveNotificationShow(true);
            requestPermission(fragment.requireActivity());
        } else if (!checkNotificationPermission(fragment.requireActivity()) && SharedPrefs.get(fragment.requireActivity()).isNotificationShown()
                && fragment.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            new NotificationPermissionDialog().show(fragment.getChildFragmentManager(), NotificationPermissionDialog.TAG);

        } else if (!checkNotificationPermission(fragment.requireActivity()) && SharedPrefs.get(fragment.requireActivity()).isNotificationShown()
                && !fragment.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                && !SharedPrefs.get(fragment.requireActivity()).isDontShowNotification()){
             new NotificationConfigurationDialog().show(fragment.getChildFragmentManager(), NotificationConfigurationDialog.TAG);

        }
    }

    public static void launchNotificationSettings(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            context.startActivity(intent);
        }
    }
}
