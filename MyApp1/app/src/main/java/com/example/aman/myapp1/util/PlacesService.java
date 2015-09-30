package com.example.aman.myapp1.util;

import android.util.Log;

import com.example.aman.myapp1.model.PlaceResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by aman on 11/9/15.
 */
public class PlacesService  {


    // https://maps.googleapis.com/maps/api/place/search/json?location=28.632808,77.218276&radius=500&types=atm&sensor=false&key=apikey

    private final String TAG = "PlacesService";
    private final String API_KEY = "AIzaSyDgpkdlHyCjPFUw-SV1BUCIJOgdAvnVUfI";


    public ArrayList<PlaceResult> findPlaces(double lat,double lng,String place){

        String url = getUrl(lat,lng,place);

        Log.i(TAG, "full_url---"+url);

        try{

            String jsonString = getJSON(url);

            Log.i(TAG, "json string return by getJSON method"+jsonString);

            JSONObject object = new JSONObject(jsonString);

            JSONArray jsonArray = object.getJSONArray("results");

            Log.i(TAG, jsonArray.toString());

            ArrayList<PlaceResult> placeResultsList = new ArrayList<PlaceResult>();

            for(int i=0;i<jsonArray.length();i++){

                try{

                placeResultsList.add(PlaceResult.getPlaceResults(jsonArray.getJSONObject(i)));

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
       return placeResultsList;

        }catch (JSONException e){
            e.printStackTrace();
        }
     return null;

   }

    public String getUrl(double lat,double lng,String place){

        StringBuilder place_url = new StringBuilder("https://maps.googleapis.com/maps/api/place/search/json?");

        if(place.isEmpty()){
        place_url.append("location=");
        place_url.append(Double.toString(lat));
        place_url.append(",");
        place_url.append(Double.toString(lng));
        place_url.append("&radius=500");
            place_url.append("&sensor=false&key="+API_KEY);
        }else{
            place_url.append("location=");
            place_url.append(Double.toString(lat));
            place_url.append(",");
            place_url.append(Double.toString(lng));
            place_url.append("&types="+place);
            place_url.append("&radius=1000");
            place_url.append("&sensor=false&key="+API_KEY);
        }

        return place_url.toString();
    }

    public String getJSON(String urlString){

        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 20);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();

        }catch (Exception e) {

            e.printStackTrace();
        }

        Log.i(TAG, "json from google place services"+content);

        return content.toString();


       /* StringBuffer buffer_string = new StringBuffer(urlString);

            String replyString = "";

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpget = new HttpGet(buffer_string.toString());

            try {

                HttpResponse response = httpclient.execute(httpget);
                InputStream is = response.getEntity().getContent();

            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(20);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            replyString = new String(baf.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return replyString.trim();*/
    }

}
