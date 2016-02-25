package com.example.aman.myapp1.util;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by aman on 17/1/16.
 */
public class AppUtil {

    public static final String FORMAT = "%02d:%02d:%02d";
    public static final String TIMER1 = "in_days";
    public static final String TIMER2 = "in_hours";
    public static final String TIMER3 = "expire";

    public static long get_current_epoch_time() {
        return (System.currentTimeMillis() / 1000);
    }

    public static String checkIsTimeToStartTimer(long server_time) {
        long oneDayTimeInMillis = 86400;
        long different = server_time - System.currentTimeMillis()/1000;
        Log.i("AppUtil", "time diff - "+different);
        if(different <= 0){
            return TIMER3;
        }else if(different == oneDayTimeInMillis || different < oneDayTimeInMillis){
            return TIMER2;
        }
        return TIMER1;
    }

    public static int calculate_hours_diff_from_now_epoch(long server_time) {
        int diff = 0;
        long different = server_time - System.currentTimeMillis() / 1000;
        long seconds_for_min = 60;
        Log.i("AppUtil", "time diff in minutes - "+(different/seconds_for_min));
        long seconds_for_hour = seconds_for_min * 60;

        diff = (int) (different / seconds_for_hour);
        Log.i("AppUtil", "time diff in hours - "+diff);

        return diff;
    }

    public static int calculate_date_diff_from_now_epoch(long server_time) {
        int diff = 0;
        long different = server_time - System.currentTimeMillis() / 1000;
        long seconds_for_min = 60;
        long seconds_for_hour = seconds_for_min * 60;
        long seconds_for_day = seconds_for_hour * 24;

        diff = (int) (different / seconds_for_day);

        return diff;
    }

}
