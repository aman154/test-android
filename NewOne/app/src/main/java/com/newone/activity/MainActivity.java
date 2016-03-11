package com.newone.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.newone.R;
import com.newone.util.Constant;
import com.newone.util.LocationUtil;
import com.newone.util.RuntimePermissionUtil;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    private GoogleMap googleMap;
    private boolean locationRuntimePermission = false;
    private SupportMapFragment supportMapFragment;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (RuntimePermissionUtil.checkRuntimeLocationPermission(this)) {
            supportMapFragment.getMapAsync(this);
        } else {
            RuntimePermissionUtil.requestLocationPermission(MainActivity.this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady");
        if (googleMap != null) {
            this.googleMap = googleMap;
            setMapLocationEnabled(true);
            setMapCompassEnable(true);
            setMapAllGesturesEnabled(true);
            setMyMapLocationButtonEnabled(true);
            CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15f).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            if(locationRuntimePermission) {
                initGoogleApiClient();
                initLocationRequest();
            }
        }
    }

    private void initGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        if (LocationUtil.isLocationEnable(this)) {
            Log.i(TAG, "onResume - LocationEnabled");
        } else {
            Log.i(TAG, "onResume - Location not Enable");
            LocationUtil.turnOnLocationSetting(MainActivity.this, locationRequest, googleApiClient);
        }
        super.onResume();
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constant.REQUEST_LOCATION) {
            Log.i(TAG, "Received response for location permissions request.");
            // We have requested multiple permissions for location, so all of them need to be
            // checked.
            if (RuntimePermissionUtil.verifyPermissions(grantResults)) {
                // All required permissions have been granted, display contacts fragment.
                supportMapFragment.getMapAsync(this);
                locationRuntimePermission = true;
            } else {
                Log.i(TAG, "Location permissions were NOT granted.");
                locationRuntimePermission = false;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Private methods for setting map components.
     */
    private void setMapCompassEnable(boolean b) {
        if (googleMap != null)
            googleMap.getUiSettings().setCompassEnabled(b);
    }

    private void setMapAllGesturesEnabled(boolean b) {
        if (googleMap != null)
            googleMap.getUiSettings().setCompassEnabled(b);
    }

    private void setMapLocationEnabled(boolean b) {
        if (googleMap != null && RuntimePermissionUtil.checkRuntimeLocationPermission(this))
            googleMap.setMyLocationEnabled(b);
    }

    private void setMyMapLocationButtonEnabled(boolean b) {
        if (googleMap != null)
            googleMap.getUiSettings().setMyLocationButtonEnabled(b);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
