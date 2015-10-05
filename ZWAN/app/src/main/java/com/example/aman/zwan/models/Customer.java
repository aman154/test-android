package com.example.aman.zwan.models;

/**
 * Created by aman on 1/10/15.
 */
public class Customer {

    private String name;
    private int mobileNum;
    private String email;
    private String password;

    public Customer(){
    }

    public Customer(String name,int mobileNum,String email,String password){
        this.name = name;
        this.mobileNum = mobileNum;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(int mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
