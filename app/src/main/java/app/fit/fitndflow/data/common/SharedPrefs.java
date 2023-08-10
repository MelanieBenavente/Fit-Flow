package app.fit.fitndflow.data.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    //keys
    private static String FILE_NAME = "FitNFlowInfo";
    private static String KEY_APIKEY = "key_apikey";

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
}
