package com.newone.util;

/**
 * Created by aman on 9/11/15.
 */
public class AppConstants {

    // Milliseconds per second
    public static final int MILLISECONDS_PER_SECOND = 1000;

    // The update interval
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;

    // A fast interval ceiling
    public static final int FAST_CEILING_IN_SECONDS = 1;

    // Update interval in milliseconds
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * UPDATE_INTERVAL_IN_SECONDS;

    // A fast ceiling of update intervals, used when the app is visible
    public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * FAST_CEILING_IN_SECONDS;

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String RECEIVER = "RECEIVER";
    public static final String RESULT_DATA_KEY =  "RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = "loc";
    public static final String CURRENT_ADDRESS = "addr";
    public static final String CURRENT_ADDRESS_LOCALITY =  "addr_loc";

    public static final int HOME_LIST = 0;
    public static final int PRO_ADD = 1;
    public static final int PRO_INCR = 2;
    public static final int PRO_DECR = 3;
    public static final int MAX_ORDER_NUMBER = 10;
    public static final int MIN_ORDER_NUMBER = 1;
    public static final String DEFUALT_PRO_ADD= "1";


    //save pref constants
    public static final String KEY_LOCAL_USER_PRODUCT = "cart_products";
    public static final String SAVE_PREF_FILE_NAME = "NEW_ONE";
    public static final String SHARED_PREF_DEFAULT_VAL_STRING = "";
    public static final int SHARED_PREF_DEFAULT_VAL_INT = Integer.MAX_VALUE;
    public static final long SHARED_PREF_DEFAULT_VAL_LONG = Long.MAX_VALUE;
    public static final String SHARED_PREF_LAT_KEY = "lat";
    public static final String SHARED_PREF_LNG_KEY = "lng";


}
