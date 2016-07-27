package com.ibusl.android.register.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.ibusl.android.register.views.ApplicationLoader;
import com.ibusl.android.register.R;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aman on 5/4/16.
 */
public class AndroidUtil {
    private static float density = 1;
    private static Boolean isTablet = null;

    public static int statusBarHeight = 0;

    static {
        density = ApplicationLoader.applicationContext.getResources().getDisplayMetrics().density;
    }

    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    public static boolean isTablet(){
        if(isTablet == null){
            isTablet = ApplicationLoader.applicationContext.getResources().getBoolean(R.bool.isTablet);
        }
        return isTablet;
    }

    public static Point getRealScreenSize(){
        Point size = new Point();
        try {
            WindowManager windowManager = (WindowManager) ApplicationLoader.applicationContext.getSystemService(Context.WINDOW_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealSize(size);
            } else {
                try {
                    Method mGetRawW = Display.class.getMethod("getRawWidth");
                    Method mGetRawH = Display.class.getMethod("getRawHeight");
                    size.set((Integer) mGetRawW.invoke(windowManager.getDefaultDisplay()), (Integer) mGetRawH.invoke(windowManager.getDefaultDisplay()));
                } catch (Exception e) {
                    size.set(windowManager.getDefaultDisplay().getWidth(), windowManager.getDefaultDisplay().getHeight());
                }
            }
        }catch (Exception e){}
        return size;
    }

    //default gray color code "#757575"
    public static void setDrawableColor(Context context, String color, int resId){
        int COLOR2 = Color.parseColor(color);
        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
        Drawable d = context.getResources().getDrawable(resId);
        d.setColorFilter(COLOR2,mMode);
    }

    public static String getDayMonthYearHourSec(long epochSeconds) {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date(epochSeconds * 1000));
    }

}
