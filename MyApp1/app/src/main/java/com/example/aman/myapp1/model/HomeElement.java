package com.example.aman.myapp1.model;

/**
 * Created by aman on m1/m7/m15.
 */
public class HomeElement {

    private int image;
    private String title;
    private long epoch;

    public HomeElement(int image, String title,long epoch){
        this.image = image;
        this.title = title;
        this.epoch = epoch;
    }

    public int getImage(){

        return image;
    }

    public String getTitle(){
        return title;
    }

    public long getEpoch(){
        return epoch;
    }
}
