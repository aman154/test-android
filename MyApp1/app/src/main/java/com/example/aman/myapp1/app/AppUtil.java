package com.example.aman.myapp1.app;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.io.InputStream;

/**
 * Created by aman on 18/9/15.
 */
public class AppUtil  {

   private static int listPosition = 0;

    public static String loadJSONFromAsset(Context context,String jsonFileName) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(jsonFileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    public static int getListPosition() {
        return AppUtil.listPosition;
    }

    public static void setListPosition(int listPosition) {
        AppUtil.listPosition = listPosition;
    }



    public static void setViewBackColor(View view, int colorType){
        view.setBackgroundColor(colorType);

    }

}
