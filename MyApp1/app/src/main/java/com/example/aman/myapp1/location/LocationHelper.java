package com.example.aman.myapp1.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.aman.myapp1.service.MyLocationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by aman on 22/4/16.
 */
public class LocationHelper implements MyFusedLocationCallback {
    private static final String TAG = "LocationHelper";
    private static final int PEN_INTENT_REQ_CODE = 100;
    private MyFusedLocation nfl = null;
    private Context context = null;
    private static final long TIME_INTERVAL = 30 * 1000;
    private int locationQuality;

    public LocationHelper(Context context){
        Log.i(TAG,"LocationHelper start");
        this.context = context;
        this.nfl = new MyFusedLocation(context,this);
        this.locationQuality = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    }

    @Override
    public void onConnectedCallBack(Bundle bundle) {
        Log.i(TAG,"onConnectedCallBack");
        if(nfl == null){
            this.nfl = new MyFusedLocation(context,this);
        }
        if(locationQuality == 0){
            this.locationQuality = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        }
        PendingIntent pi = getPendingIntent();
        nfl.stopLocationUpdates(pi);
        //Cancel pending intent
        if (pi != null) {
            pi.cancel();
        }
        nfl.startLocationUpdates(TIME_INTERVAL, this.locationQuality, getPendingIntent());
        nfl.disconnect();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChangedCallBack(Location location, boolean locationUpdateTimedOut) {

    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(context, MyLocationIntentService.class);
        return PendingIntent.getService(context,PEN_INTENT_REQ_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
