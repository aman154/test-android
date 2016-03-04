package com.newone.util;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;

/**
 * Created by aman on 18/11/15.
 */
public interface FindUserLocationCallback {

    void onConnectedCallback(Bundle bundle);
    void onConnectionSuspendedCallback(int i);
    void onConnectionFailed(ConnectionResult connectionResult);
    void onLocationChangedCallback(Location location);
}
