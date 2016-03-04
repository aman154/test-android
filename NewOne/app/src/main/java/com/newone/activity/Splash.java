package com.newone.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.newone.R;
import com.newone.service.FetchAddressIntentService;
import com.newone.util.AppConstants;
import com.newone.util.AppUtil;
import com.newone.util.FindUserLocation;
import com.newone.util.FindUserLocationCallback;

public class Splash extends Activity {
    private static final String LOG_TAG = "splash";

    private FindUserLocation findUserLocation;
    private Location mLastLocation;
    private AddressResultReceiver mResultReceiver;
    private ProgressBar progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressDialog = (ProgressBar) findViewById(R.id.splash_progress_dialog);
        mResultReceiver = new AddressResultReceiver(new Handler());
        if(AppUtil.isConnectingToInternet(this)){
            new SplashAsycTask().execute();
        }else{
            Dialog dialog = new Dialog(this);
            dialog.setTitle("No internet connection");
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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


    class SplashAsycTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            progressDialog.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            getCurrentLocation();
            return is_get_location(mLastLocation);
        }

        @Override
        protected void onPostExecute(Boolean b) {

        }
    }

    public void getCurrentLocation() {
        findUserLocation = new FindUserLocation(this, new FindUserLocationCallback() {
            @Override
            public void onConnectedCallback(Bundle bundle) {
                mLastLocation = findUserLocation.getLastLocation();
                startIntentService();
            }
            @Override
            public void onConnectionSuspendedCallback(int i) {
                progressDialog.setVisibility(View.GONE);

            }
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                progressDialog.setVisibility(View.GONE);
            }
            @Override
            public void onLocationChangedCallback(Location location) {
            }
        });

    }

    private boolean is_get_location(Location location){
        return location != null;
    }

    private void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(AppConstants.RECEIVER, mResultReceiver);
        intent.putExtra(AppConstants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    private void startMainActivity(String mLocalAddress){
        Intent intent = new Intent(Splash.this,HomeActivity.class);
        intent.putExtra(AppConstants.CURRENT_ADDRESS_LOCALITY, mLocalAddress);
        startActivity(intent);
        finish();
    }


     // Receiver for data sent from FetchAddressIntentService.
    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }
       // Receives data sent from FetchAddressIntentService.
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String mAddressOutput = null;
            String mLocalAddress = null;
            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(AppConstants.CURRENT_ADDRESS);
            mLocalAddress = resultData.getString(AppConstants.CURRENT_ADDRESS_LOCALITY);
            Log.i(LOG_TAG,"full_address - "+mAddressOutput+" and "+"local_address - "+mLocalAddress);

            // Show a toast message if an address was found.
            if (resultCode == AppConstants.SUCCESS_RESULT) {
                progressDialog.setVisibility(View.GONE);
                Toast.makeText(Splash.this, "address_found", Toast.LENGTH_LONG).show();
                startMainActivity(mLocalAddress);
            }
        }
    }

}
