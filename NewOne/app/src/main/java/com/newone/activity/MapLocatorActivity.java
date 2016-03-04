package com.newone.activity;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.newone.R;
import com.newone.util.UserLocation;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;


public class MapLocatorActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "MapLocatorActivity";

    SupportMapFragment mapFragment;
    GoogleApiClient googleApiClient;
    EditText mapSearchEdit;
    TextView mapLoactionUpdateText;
    Location location;
    String localAddress;
    LatLng center;
    Address address;
    int status;
    double lat,lng;
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_locator);

        mapSearchEdit = (EditText) findViewById(R.id.map_search_editview);
        mapLoactionUpdateText = (TextView) findViewById(R.id.location_update_text);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if(status != ConnectionResult.SUCCESS){

            int reqCode = 29;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,this,reqCode);
            dialog.show();
        }
        else{

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_locator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


        @Override
        public void onMapReady(final GoogleMap map) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setAllGesturesEnabled(true);

            if(googleApiClient.isConnected()) {
                location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            if(lat != 0 && lng != 0){

                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(16f).build();

                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                map.clear();

                map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {

                        center = map.getCameraPosition().target;

                        new GetLocationAsync(center.latitude,center.longitude).execute();
                    }
                });
            }

        }

    @Override
    public void onConnected(Bundle bundle) {
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class GetLocationAsync extends AsyncTask<String, Void, String> {

        double x, y;
        StringBuilder str;

        public GetLocationAsync(double latitude, double longitude) {

            x = latitude;
            y = longitude;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                geocoder = new Geocoder(MapLocatorActivity.this, Locale.getDefault());
                addresses = geocoder.getFromLocation(x, y, 1);
                str = new StringBuilder();
                if (Geocoder.isPresent() && addresses != null && addresses.size() > 0) {
                    Address returnAddress = addresses.get(0);

                    String localityString = returnAddress.getLocality();
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    str.append(localityString + "");
                    str.append(city + "" + region_code + "");
                    str.append(zipcode + "");
                    Log.i("MapLocatorActivity", "current full add - " + str);
                    Log.i(TAG, "address ->"+"get local -"+returnAddress.getLocale()+","+"get feature name- "+returnAddress.getFeatureName()+","+"get admin area -"+returnAddress.getAdminArea()+","+"get subAdmin area -"+returnAddress.getSubAdminArea()+","+"get locality -"+returnAddress.getLocality()+","+"get subLocal -"+returnAddress.getSubLocality()+","+"get thoroughfare- "+returnAddress.getThoroughfare()+","+"get subThoroughfare- "+returnAddress.getSubThoroughfare());


                } else {
                }
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG,"address_line0-"+addresses.get(0).getAddressLine(0)+","+"address_line1"+addresses.get(0).getAddressLine(1));
            mapLoactionUpdateText.setText(addresses.get(0).getAddressLine(0)
                    + addresses.get(0).getAddressLine(1) + " ");
        }

    }
}
