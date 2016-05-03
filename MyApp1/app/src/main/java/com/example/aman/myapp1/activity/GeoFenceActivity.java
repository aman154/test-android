package com.example.aman.myapp1.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.service.GeofenceTransitionsIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.ResultCallback;

import java.util.ArrayList;

public class GeoFenceActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {
    private static final String TAG = "GeoFenceActivity";
    private static final float GEOFENCE_RADIUS_IN_METERS = 20;
    private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 1000 * 60 * 25;
    private GoogleApiClient googleApiClient;
    private ArrayList<Geofence> geofenceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fence);

        geofenceList = new ArrayList<Geofence>();
        populateGeofenceList();

        buildGoogleApiClient();
    }

    private void buildGoogleApiClient(){
        googleApiClient =  new GoogleApiClient.Builder(GeoFenceActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private GeofencingRequest buildGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    public void addGeofences() {
        if (!googleApiClient.isConnected()) {
            Toast.makeText(this, "GoogleApiClient not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    buildGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this);
        } catch (SecurityException securityException) {
            securityException.printStackTrace();
        }
    }

    public void removeGeofences() {
        if (!googleApiClient.isConnected()) {
            Toast.makeText(this, "GoogleApiClient not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            LocationServices.GeofencingApi.removeGeofences(
                    googleApiClient,
                    getGeofencePendingIntent()
            ).setResultCallback(this);
        } catch (SecurityException securityException) {
            securityException.printStackTrace();
        }
    }

    public void populateGeofenceList() {
        Log.i(TAG,"populateGeofenceList");
            geofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId("my_geofence")
                    // Set the circular region of this geofence.
                    .setCircularRegion(
                            12.8868045,
                            77.5959121,
                            GEOFENCE_RADIUS_IN_METERS)
                    // Set the expiration duration of the geofence. This geofence gets automatically
                    // removed after this period of time.
                    .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)

                    // Set the transition types of interest. Alerts are only generated for these
                    // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT | Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
        if(googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG,"onConnected");
        addGeofences();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG,"onConnectionSuspended");
        removeGeofences();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG,"onConnectionFailed");
        removeGeofences();
    }


    @Override
    public void onResult(@NonNull Status status) {
        Log.i(TAG,"onResult");
        if(status.isSuccess()){
            Log.i(TAG,"GeoFence added successful");
        }else {
            Log.i(TAG,"GeoFence not added");
        }
    }
}
