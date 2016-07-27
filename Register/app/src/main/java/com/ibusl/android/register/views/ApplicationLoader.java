package com.ibusl.android.register.views;

import android.app.Application;
import android.content.Context;

/**
 * Created by aman on 5/4/16.
 */
public class ApplicationLoader extends Application {

    public static volatile Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();
    }
}
