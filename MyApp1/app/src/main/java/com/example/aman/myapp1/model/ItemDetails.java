package com.example.aman.myapp1.model;

/**
 * Created by aman on 18/9/15.
 */
public class ItemDetails {

    private String iDetail;
    private String iPic;
    private Integer mrPrice;
    private Integer dlPrice;
    private Double rating;

    public String getiDetail() {
        return iDetail;
    }

    public void setiDetail(String iDetail) {
        this.iDetail = iDetail;
    }

    public String getiPic() {
        return iPic;
    }

    public void setiPic(String iPic) {
        this.iPic = iPic;
    }

    public Integer getMrPrice() {
        return mrPrice;
    }

    public void setMrPrice(Integer mrPrice) {
        this.mrPrice = mrPrice;
    }

    public Integer getDlPrice() {
        return dlPrice;
    }

    public void setDlPrice(Integer dlPrice) {
        this.dlPrice = dlPrice;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
