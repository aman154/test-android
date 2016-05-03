package com.example.aman.myapp1.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.aman.myapp1.receiver.AlarmBroadcastReceiver;
import com.example.aman.myapp1.service.AlarmIntentService;

/**
 * Created by aman on 22/4/16.
 */
public class AlarmUtil {
    private static final String TAG = "AlarmUtil";
    private static final int reqCode = 121;
    private static final long ALARM_START_BUFFER_MS = 2 * 1000;
    private static final long ALARM_RESET_TIME_MS = 5 * 60 * 1000;


    public static void startAlarmWithIntent(Context context) {
        Log.i(TAG, "startAlarmWithIntent");
        Intent intent = new Intent(context, AlarmIntentService.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        if (pendingIntent != null) {
            pendingIntent.cancel();
        }
        intent = new Intent(context, AlarmIntentService.class);
        pendingIntent = PendingIntent.getService(context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        setInexactRepeating(alarmManager, pendingIntent);
    }

    public static void startAlarmWithBroadcast(Context context) {
        Log.i(TAG, "startAlarmWithBroadcast");
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        if (pendingIntent != null) {
            pendingIntent.cancel();
        }
        intent = new Intent(context, AlarmBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        setInexactRepeating(alarmManager, pendingIntent);
    }

    private static void setAlarm(AlarmManager alarmManager, PendingIntent pendingIntent) {
        if (alarmManager != null && pendingIntent != null) {
            Log.i(TAG, "setAlarm");
            alarmManager.set(AlarmManager.RTC, ALARM_RESET_TIME_MS, pendingIntent);
        }
    }

    private static void setRepeating(AlarmManager alarmManager, PendingIntent pendingIntent) {
        if (alarmManager != null && pendingIntent != null) {
            Log.i(TAG, "setRepeating");
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_RESET_TIME_MS, pendingIntent);
            Log.i(TAG, "setRepeating  triggerAtMillis - " + System.currentTimeMillis() + ", intervalMillis -  " + ALARM_RESET_TIME_MS);
        }
    }


    private static void setInexactRepeating(AlarmManager alarmManager, PendingIntent pendingIntent) {
        if (alarmManager != null && pendingIntent != null) {
            Log.i(TAG, "setInexactRepeating");
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + ALARM_START_BUFFER_MS, ALARM_RESET_TIME_MS, pendingIntent);
            Log.i(TAG, "setInexactRepeating  triggerAtMillis - " + SystemClock.elapsedRealtime() + ALARM_START_BUFFER_MS + ", intervalMillis -  " + ALARM_RESET_TIME_MS);
        }
    }

    //
    private static void setExact(AlarmManager alarmManager, PendingIntent pendingIntent) {
       /* if(alarmManager != null && pendingIntent != null){
            alarmManager.setExact(AlarmManager.RTC,cu,ALARM_RESET_TIME_MS,pendingIntent);
        }*/
    }

    //
    private static void setWindow(AlarmManager alarmManager, PendingIntent pendingIntent) {
      /*  if(alarmManager != null && pendingIntent != null){
            alarmManager.setWindow(AlarmManager.RTC,ALARM_RESET_TIME_MS,ALARM_RESET_TIME_MS,pendingIntent);
        }*/
    }

}
