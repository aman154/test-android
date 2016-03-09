package com.example.aman.myapp1.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aman on 8/3/16.
 */
public class PlaceRoot implements Serializable {

    private String pageToken;

    private String status;

    private ArrayList<PlaceResult> placeResults;

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public  ArrayList<PlaceResult> getPlaceResult() {
        return placeResults;
    }

    public void setPlaceResult( ArrayList<PlaceResult> placeResults) {
        this.placeResults = placeResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
