package com.example.aman.myapp1.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.aman.myapp1.location.LocationHelper;

/**
 * Created by aman on 22/4/16.
 */
public class AlarmIntentService extends IntentService {
    private static final String TAG = "AlarmIntentService";
    LocationHelper locationHelper = null;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public AlarmIntentService() {
        super("AlarmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null){
            Log.i(TAG,"onHandleIntent");
            locationHelper = new LocationHelper(this);

            Intent intent1 = new Intent(this, NetworkIntentService.class);
            intent1.putExtra("test_intent","test_success");
            this.startService(intent1);
        }
    }
}
