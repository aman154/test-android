package com.example.aman.myapp1.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by aman on 11/9/15.
 */
public class PlaceResult implements Serializable {

    private String name;
    private String icon;
    private String id;
    private Double lat;
    private Double lng;
    private String address;
    private Double rating;
    private String photoRef;
    private static final double DefaultRating = 3.0;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public static PlaceResult getPlaceResults(JSONObject object){

        PlaceResult placeResult = new PlaceResult();

        try{
        JSONObject geoObject = object.getJSONObject("geometry");

            JSONObject locObject = geoObject.getJSONObject("location");
            placeResult.setLat(locObject.getDouble("lat"));
            placeResult.setLng(locObject.getDouble("lng"));

            placeResult.setIcon(object.getString("icon"));
            placeResult.setId(object.getString("id"));
            placeResult.setName(object.getString("name"));

            if(object.has("photos")){
            if(object.getJSONArray("photos").getJSONObject(0).has("photo_reference")){
            placeResult.setPhotoRef(object.getJSONArray("photos").getJSONObject(0).getString("photo_reference"));}}

            if(object.has("rating")){
            placeResult.setRating(object.getDouble("rating"));}else {placeResult.setRating(DefaultRating);}

            placeResult.setAddress(object.getString("vicinity"));


            return placeResult;
        }catch(JSONException e){
            e.printStackTrace();
        }
       return null;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }
}
