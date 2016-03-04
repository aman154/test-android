package com.newone.util;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.InputStream;

/**
 * Created by aman on 3/11/15.
 */
public class AppUtil {

    /**
     * Checking for all possible internet providers
     * **/
    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static Boolean check_internet_connection(Context activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


    public static String loadJSONFromAsset(Activity context,String jsonName) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(jsonName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public static boolean savePref(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.SAVE_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        boolean saved = editor.commit();
        return saved;
    }

    public static boolean savePref(Context context, String key, long value) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.SAVE_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        boolean saved = editor.commit();
        return saved;
    }

    public static boolean savePref(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(AppConstants.SAVE_PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        boolean saved = editor.commit();
        return saved;
    }

    public static int getPrefInt(Context context, String key) {
        return (context.getSharedPreferences(AppConstants.SAVE_PREF_FILE_NAME, Context.MODE_PRIVATE)).getInt(key, AppConstants.SHARED_PREF_DEFAULT_VAL_INT);
    }

    public static long getPrefLong(Context context, String key) {
        return (context.getSharedPreferences(AppConstants.SAVE_PREF_FILE_NAME, Context.MODE_PRIVATE)).getLong(key, AppConstants.SHARED_PREF_DEFAULT_VAL_LONG);
    }

    public static String getPrefString(Context context, String key) {
        return (context.getSharedPreferences(AppConstants.SAVE_PREF_FILE_NAME, Context.MODE_PRIVATE)).getString(key, AppConstants.SHARED_PREF_DEFAULT_VAL_STRING);
    }

}
