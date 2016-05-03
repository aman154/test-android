package com.example.aman.myapp1.location;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.aman.myapp1.activity.CircularViewPagerActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by aman on 22/4/16.
 */
public class MyFusedLocation implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "MyFusedLocation";
    private static final int REQUEST_CHECK_SETTINGS_CODE = 1231;
    private Context context;
    private MyFusedLocationCallback myFusedLocationCallback;
    private GoogleApiClient googleApiClient;

    public MyFusedLocation(Context context, MyFusedLocationCallback myFusedLocationCallback) {
        this.context = context;
        this.myFusedLocationCallback = myFusedLocationCallback;

        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        this.myFusedLocationCallback.onConnectedCallBack(bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        this.myFusedLocationCallback.onConnectionSuspended(i);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.myFusedLocationCallback.onLocationChangedCallBack(location, false);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.myFusedLocationCallback.onConnectionFailed(connectionResult);
    }

    public void startLocationUpdates(long time_interval, int priority, PendingIntent pi) {
        if (is_connected()) {
            Log.i(TAG, "startLocationUpdates");

             LocationRequest request = LocationRequest.create()
                    .setInterval(time_interval)
                    .setFastestInterval(time_interval)
                    .setPriority(priority);
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, pi);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    public void stopLocationUpdates(PendingIntent pi) {
        if (is_connected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, pi);
        }
    }

    public void disconnect() {
        if (is_connected()) {
            this.googleApiClient.disconnect();
        }
    }

    private boolean is_connected() {
        return this.googleApiClient.isConnected();
    }
}
