package app.fit.fitndflow.ui.features.common.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.fit.fitndflow.R;

import app.fit.fitndflow.ui.features.common.MainActivity;

public class MyNotificationManager extends BroadcastReceiver {
    private static final int NOTIFICATION_REQUEST_CODE = 1;

    @Override
    public void onReceive(Context context, Intent intent) {


        printNotification(context, intent);
    }

    public void printNotification(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, notificationIntent, PendingIntent.FLAG_MUTABLE);

        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("text");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "Channel name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

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

        int notificationId = (int) System.currentTimeMillis();
        try {
            manager.notify(notificationId, notification);
        } catch (SecurityException e) {
            Log.e("Notification", e.getMessage());
        }
    }

    public void scheduleNotification(MainActivity context, long delayMillis) {
        if (context.checkNotificationPermission()) {

            Intent intent = new Intent(context, MyNotificationManager.class);
            intent.putExtra("title", context.getString(R.string.notification_title));
            intent.putExtra("text", context.getString(R.string.notification_body));
            int unicId = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, unicId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayMillis, pendingIntent);
        } else {
            context.requestPermission();
        }
    }
}
