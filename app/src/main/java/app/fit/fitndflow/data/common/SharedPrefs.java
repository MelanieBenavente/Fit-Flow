package app.fit.fitndflow.data.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefs {
    //keys
    private static String FILE_NAME = "FitNFlowInfo";
    private static String KEY_APIKEY = "key_apikey";
    private final SharedPreferences mPrefs;


    private static final String PREFS_NAME = "MRBEP_PREFS";
    private static final String IS_NOTIFICATION_SHOWN = "NOTIFICATION_SHOWN";
    private static final String DONT_SHOW_NOTIFICATIONS_AGAIN = "DONT_SHOW_NOTIFICATIONS_AGAIN";


    public void logOut() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(IS_NOTIFICATION_SHOWN, false);
        editor.putBoolean(DONT_SHOW_NOTIFICATIONS_AGAIN, false);
        editor.apply();
    }


    public static void saveApikeyToSharedPRefs(Context context, String value){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_APIKEY, value);
        editor.commit();
    }

    public static String getApikeyFromSharedPRefs(Context context){
        return getSharedPreferences(context).getString(KEY_APIKEY, null);
    }

    public static void removeAllUserData(Context context){
        getSharedPreferences(context).edit().clear().commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(FILE_NAME, 0); // 0 - for private mode
    }

    public void saveDontShowNotification(boolean dontShowNotification){
        saveBoolean(DONT_SHOW_NOTIFICATIONS_AGAIN, dontShowNotification);
    }

    public boolean isDontShowNotification(){
        return mPrefs.getBoolean(DONT_SHOW_NOTIFICATIONS_AGAIN, false);
    }

    public void saveNotificationShow(boolean notificationShown) {
        saveBoolean(IS_NOTIFICATION_SHOWN, notificationShown);
    }
    public boolean isNotificationShown(){
        return mPrefs.getBoolean(IS_NOTIFICATION_SHOWN, false);
    }

    private void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
        Log.d("OK", "grabando en shared");
    }

    private void saveString(String key, String value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, value);
        editor.apply();
        Log.d("OK", "grabando en shared");
    }

    private void saveInt(String key, int value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(key, value);
        editor.apply();
        Log.d("OK", "grabando en shared");
    }


    private SharedPrefs(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    //controla el archivo de preferencias guardadas
    private static SharedPrefs INSTANCE;

    //patron singleton
    public static SharedPrefs get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPrefs(context);
        }
        return INSTANCE;
    }
}
