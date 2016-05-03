package com.example.aman.myapp1.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.aman.myapp1.receiver.AlarmBroadcastReceiver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by aman on 25/4/16.
 */
public class MyLocationService extends IntentService implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "MyLocationService";
    private static final long DEFAULT_LOCATION_REQ_TIME_INTERVAL = 1000 * 30;
    private static final int ALARM_REQ_CODE  = 1010;
    private GoogleApiClient googleApiClient;
    private  AlarmManager alarmManager;
    private static boolean fetchingLocation = false;


    public MyLocationService() {
        super("MyLocationService");
    }

   /* @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
    }*/

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
      *//*  initGoogleApiClient();
        connectGoogleApiClient();*//*
        return START_STICKY;
    }*/

    /*@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }*/


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected");
        startLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged");
        if (location != null) {
            Log.i(TAG, "location - lat - " + location.getLatitude() + ", lng - " + location.getLongitude() + ", accuracy - "+location.getAccuracy());
            startAlarmBroadcast(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG,"onConnectionSuspended"); }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG,"onConnectionFailed"); }

    /*@Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        stopLocationUpdates();
        clearAlarmBroadcast();
        disconnect();
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent");
        if(intent != null){
            if(fetchingLocation) {
                clearAlarmBroadcast();
                stopLocationUpdates();
            }
            initGoogleApiClient();
            connectGoogleApiClient();
        }
    }

    private void initGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void connectGoogleApiClient(){
        if(googleApiClient != null){
            if(!(googleApiClient.isConnected() || googleApiClient.isConnecting())){
                googleApiClient.connect();
            }else {
                startLocationUpdates();
            }
        }
    }

    private void startLocationUpdates() {
        Log.i(TAG, "startLocationUpdates");
        try {
            fetchingLocation = true;
            LocationRequest request = LocationRequest.create()
                    .setInterval(DEFAULT_LOCATION_REQ_TIME_INTERVAL)
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void stopLocationUpdates() {
        Log.i(TAG, "stopLocationUpdates");
        fetchingLocation = false;
        if (is_connected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    private void disconnect() {
        if (is_connected()) {
            this.googleApiClient.disconnect();
        }
    }

    private boolean is_connected() {
        return googleApiClient != null && googleApiClient.isConnected();
    }

    private void startAlarmBroadcast(Location location){
        Log.i(TAG,"startAlarmBroadcast");
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        intent.putExtra("location",location);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,ALARM_REQ_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),pendingIntent);
    }

    private void clearAlarmBroadcast(){
        Log.i(TAG,"clearAlarmBroadcast");
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,ALARM_REQ_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        if (pendingIntent != null) {
            pendingIntent.cancel();
        }
    }

}
