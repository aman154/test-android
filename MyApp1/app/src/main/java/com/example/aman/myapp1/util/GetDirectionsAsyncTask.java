package com.example.aman.myapp1.util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.aman.myapp1.activity.MapActivity;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by aman on 13/7/15.
 */
public class GetDirectionsAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList<LatLng>> {

    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";
    private MapActivity activity;
    private String url;

    private Exception exception;

    private ProgressDialog progressDialogBox;

    public GetDirectionsAsyncTask(MapActivity activity /*String url*/)
    {
        super();
        this.activity = activity;

        //  this.url = url;
    }

    public void onPreExecute() {
        progressDialogBox = new ProgressDialog(activity);
        progressDialogBox.setMessage("please wait...");
        progressDialogBox.show();
    }

    @Override
    public void onPostExecute(ArrayList<LatLng> result) {
        progressDialogBox.dismiss();

        if (exception == null) {
            activity.handleGetDirectionsResult(result);
        } else {
            processException();
        }
    }

    @Override
    protected ArrayList<LatLng> doInBackground(Map<String, String>... params) {

        Map<String, String> paramMap = params[0];
        try{
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
            GetMapDirection md = new GetMapDirection();
            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
            ArrayList<LatLng> directionPoints = md.getDirection(doc);
            return directionPoints;

        }
        catch (Exception e) {
            exception = e;
            return null;
        }
    }

    private void processException() {
        Toast.makeText(activity, "error_when_retrieving_data", Toast.LENGTH_SHORT).show();
    }

}
