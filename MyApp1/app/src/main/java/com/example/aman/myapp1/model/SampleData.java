package com.example.aman.myapp1.model;

/**
 * Created by root on 23/4/15.
 */
public class SampleData {
    String title;
    boolean normal=true;

    public String getTitle() {
        return title;
    }

    public SampleData setTitle(String title) {
        this.title = title; return this;
    }

    public boolean isNormal() {
        return normal;
    }

    public SampleData setNormal(boolean normal) {
        this.normal = normal; return this;
    }
}
