package com.newone.util;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

/**
 * Created by aman on 18/11/15.
 */
public class FindUserLocation implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private FindUserLocationCallback locationCallback;
    private GoogleApiClient googleApiClient;

   public FindUserLocation(Context context,FindUserLocationCallback locationCallback){

       this.locationCallback = locationCallback;
       googleApiClient = new GoogleApiClient.Builder(context)
               .addConnectionCallbacks(this)
               .addOnConnectionFailedListener(this)
               .addApi(LocationServices.API)
               .build();
       googleApiClient.connect();


   }

    public Location getLastLocation(){
        Location location = null;

        if(is_connected()) {
            location = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        }
        return location;
    }


    @Override
    public void onConnected(Bundle bundle) {
        this.locationCallback.onConnectedCallback(bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        this.locationCallback.onConnectionSuspendedCallback(i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.locationCallback.onConnectionFailed(connectionResult);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.locationCallback.onLocationChangedCallback(location);
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
