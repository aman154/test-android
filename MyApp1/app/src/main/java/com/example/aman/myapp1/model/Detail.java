package com.example.aman.myapp1.model;

/**
 * Created by aman on 29/m6/m15.
 */
public class Detail {

    private String  rate;
    private String rateDetail;
    private String rateType;
    private String validity;

    public Detail(){
        super();
    }

    public Detail(String rate,String rateType,String rateDetail,String validity){

        this.setRate(rate);
        this.setRateType(rateType);
        this.setRateDetail(rateDetail);
        this.setValidity(validity);
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateDetail() {
        return rateDetail;
    }

    public void setRateDetail(String rateDetail) {
        this.rateDetail = rateDetail;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
