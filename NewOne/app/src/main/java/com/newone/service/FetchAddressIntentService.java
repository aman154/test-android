package com.newone.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import com.newone.util.AppConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by aman on 19/11/15.
 */
public class FetchAddressIntentService extends IntentService {

    private static final String TAG = "FAddressIntentService";
    ResultReceiver mReceiver;

    public FetchAddressIntentService(){
        super("FetchAddressIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        mReceiver = intent.getParcelableExtra(AppConstants.RECEIVER);
        Location location = intent.getParcelableExtra(AppConstants.LOCATION_DATA_EXTRA);

        if(location == null){

        }
       // Address found using the Geocoder.
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException ioException) {
            errorMessage = "service_not_available";//getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "invalid_lat_long_used"; //getString(R.string.invalid_lat_long_used);
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "no_address_found";//getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(AppConstants.FAILURE_RESULT, errorMessage,errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();
            Log.i(TAG, "address ->"+"get local -"+address.getLocale()+","+"get feature name- "+address.getFeatureName()+","+"get admin area -"+address.getAdminArea()+","+"get subAdmin area -"+address.getSubAdminArea()+","+"get locality -"+address.getLocality()+","+"get subLocal -"+address.getSubLocality()+","+"get thoroughfare- "+address.getThoroughfare()+","+"get subThoroughfare- "+address.getSubThoroughfare());
            // Fetch the address lines using {@code getAddressLine},
            // join them, and send them to the thread. The {@link android.location.address}
            // class provides other options for fetching address details that you may prefer
            // to use. Here are some examples:
            // getLocality() ("Mountain View", for example)
            // getAdminArea() ("CA", for example)
            // getPostalCode() ("94043", for example)
            // getCountryCode() ("US", for example)
            // getCountryName() ("United States", for example)
            String localAdd = getLocalAdd(address);

            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));

                Log.i(TAG, "address ->"+address.getAddressLine(i));
            }
            Log.i(TAG, "address_found");//getString(R.string.address_found)
            Log.i(TAG, "address-"+TextUtils.join(System.getProperty("line.separator"), addressFragments)+","+"local address- "+localAdd);
            deliverResultToReceiver(AppConstants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"), addressFragments), localAdd);
        }

    }

    private void deliverResultToReceiver(int resultCode, String fullAddress,String locality) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.CURRENT_ADDRESS, fullAddress);
        bundle.putString(AppConstants.CURRENT_ADDRESS_LOCALITY, locality);
        mReceiver.send(resultCode, bundle);
    }


    private String getLocalAdd(Address address){
        List<String> sbStrings;
        String addString = address.getAddressLine(1);
        sbStrings =  Arrays.asList(addString.split(","));
        int len = sbStrings.size();
        return sbStrings.get(len-1);

    }
}
