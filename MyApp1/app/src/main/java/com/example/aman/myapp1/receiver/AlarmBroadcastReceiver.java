package com.example.aman.myapp1.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.aman.myapp1.location.LocationHelper;
import com.example.aman.myapp1.service.NetworkIntentService;

/**
 * Created by aman on 22/4/16.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmBroadcastReceiver";
    LocationHelper locationHelper = null;


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            Location location = intent.getParcelableExtra("location");
            if (location != null) {
                Log.i(TAG, "location - lat - " + location.getLatitude() + ", lng - " + location.getLongitude() + ", accuracy - " + location.getAccuracy());
            }
        }
       /* locationHelper = new LocationHelper(context);

        Intent intent1 = new Intent(context, NetworkIntentService.class);
        intent1.putExtra("test_intent","test_success");
        context.startService(intent1);*/
    }
}
