package com.example.aman.myapp1.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by aman on 22/4/16.
 */
public class MyLocationIntentService extends IntentService {
    private static final String TAG = "MyLocationService";

    public MyLocationIntentService() {
        super("MyLocationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"onHandleIntent");
        if(intent != null){
            Bundle b = intent.getExtras();
            Location location = (Location) b.get(FusedLocationProviderApi.KEY_LOCATION_CHANGED);
            //To support Location manager in case Google play services are not there
            if (location == null) {
                location = (Location) b.get(LocationManager.KEY_LOCATION_CHANGED);
            }
            if (location != null) {
                Log.i(TAG, "location - lat - " + location.getLatitude() + ", lng - " + location.getLongitude() + ", accuracy - "+location.getAccuracy());
               /* Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String formattedDate = df.format(c.getTime());
                try {
                    JSONObject loc = new JSONObject();
                    loc.put("lat", location.getLatitude());
                    loc.put("lng", location.getLongitude());
                    loc.put("ac", location.getAccuracy());
                    loc.put("ti", formattedDate);
                    post("https://demo6939494.mockable.io/location_moto",loc.toString(), "application/json");
                }catch (Exception e){
                    e.printStackTrace();
                }*/

            }
        }
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(true);
    }

    /**
     * This method will not post data in async manner and should not be called directly from main UI thread
     *
     * @param serverUrl
     * @param data
     * @param contentType
     * @return status code and text response
     * @throws IOException
     */
    public static String[] post(String serverUrl, String data, String contentType) throws IOException {
        String[] response = new String[2];
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        try {
            Log.i(TAG, "POST_URL:" + serverUrl + " CONTENT_TYPE:" + contentType);
            Log.i(TAG, "POSTING_DATA:" + data);
            url = new URL(serverUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-type", contentType);
            httpURLConnection.connect();

            dataOutputStream = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            //dataOutputStream.writeBytes(URLEncoder.encode(data, "UTF-8"));
            dataOutputStream.writeBytes(data);

            int sCode = httpURLConnection.getResponseCode();
            response[0] = sCode + "";

            Log.i(TAG, "POST_RESPONSE_CODE" + sCode);

            BufferedReader is = new BufferedReader(new InputStreamReader(
                    httpURLConnection.getInputStream()));
            String _line = null;
            String respText = "";
            while (((_line = is.readLine()) != null)) {
                respText = respText + _line;
            }
            response[1] = respText;
            if (is != null) {
                is.close();
            }
        } finally {
            if (dataOutputStream != null) {
                dataOutputStream.flush();
                dataOutputStream.close();
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return response;
    }
}
