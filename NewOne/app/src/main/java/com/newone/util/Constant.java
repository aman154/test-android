package com.newone.util;

import android.Manifest;

/**
 * Created by aman on 2/3/16.
 */
public class Constant {

    public static final int REQUEST_CHECK_SETTINGS = 106;
    //Id to identify a contacts permission request.
    public static final int REQUEST_LOCATION = 1;
    // Permissions required to read and write contacts.
    public static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
}
