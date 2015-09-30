package com.example.aman.myapp1.model;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by aman on 16/9/15.
 */
public class PlacesSearchResult {


    private ArrayList<PlaceResult> placeSearchList = new ArrayList<PlaceResult>();

    public ArrayList<PlaceResult> getPlaceSearchList() {
        return placeSearchList;
    }

    public void setPlaceSearchList(ArrayList<PlaceResult> placeSearchList) {
        this.placeSearchList = placeSearchList;
    }
}
