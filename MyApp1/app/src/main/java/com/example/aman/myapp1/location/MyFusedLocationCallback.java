package com.example.aman.myapp1.location;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;

/**
 * Created by aman on 22/4/16.
 */
public interface MyFusedLocationCallback {
     void onConnectedCallBack(Bundle bundle);
     void onConnectionSuspended(int i);
     void onConnectionFailed(ConnectionResult connectionResult);
     void onLocationChangedCallBack(Location location, boolean locationUpdateTimedOut);
}
